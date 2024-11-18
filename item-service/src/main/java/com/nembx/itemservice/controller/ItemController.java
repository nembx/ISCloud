package com.nembx.itemservice.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nembx.itemservice.domain.Item;
import com.nembx.itemservice.result.CommonResult;
import com.nembx.itemservice.result.PageResult;
import com.nembx.itemservice.service.ItemService;
import com.nembx.itemservice.utils.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Lian
 */
@RestController
@Slf4j
@RequestMapping("/item")
@Tag(name = "商品接口", description = "商品接口文档")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Operation(summary = "查询所有商品接口")
    @GetMapping("/getAllItem")
    public CommonResult<List<Item>> getItem(){
        redisUtil.setKey("Item", JSONUtil.toJsonStr(itemService.listMaps()));
        redisUtil.expireSeconds("Item", 10);
        List<Item> list = JSONUtil.parseArray(redisUtil.getKey("Item")).toList(Item.class);
        return new CommonResult<>(200, "查询成功",list);
    }

    @Operation(summary = "分页")
    @GetMapping("/findPage/{pageCurrent}")
    public PageResult<List<Item>> findPage(@PathVariable long pageCurrent){
        if (redisUtil.hasKey("Item:" + pageCurrent)){
            List<Item> list = JSONUtil.parseArray(redisUtil.getKey("Item:" + pageCurrent)).toList(Item.class);
            long itemTotal = Integer.parseInt(redisUtil.getKey("Item:Total"));
            log.info("从缓存中获取数据");
            return new PageResult<>(200, "查询成功", itemTotal,itemTotal / 12 + 1,pageCurrent,list);
        }
        IPage<Item> page = itemService.page(new Page<>(pageCurrent, 12));
        log.info("从数据库中获取数据");
        redisUtil.setKey("Item:" + pageCurrent, JSONUtil.toJsonStr(page.getRecords()));
        redisUtil.expireSeconds("Item:" + pageCurrent, 20);
        redisUtil.setKey("Item:Total", String.valueOf(page.getTotal()));
        List<Item> list = JSONUtil.parseArray(redisUtil.getKey("Item:" + pageCurrent)).toList(Item.class);
        return new PageResult<>(200, "查询成功",page.getTotal(),page.getPages(),pageCurrent,list);
    }

    @Operation(summary = "查询某商品接口")
    @GetMapping("/searchItem/{itemName}")
    public CommonResult<List<Map<String,Object>>> searchItem(@PathVariable String itemName){
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("item_name",itemName);
        if (CollUtil.isEmpty(itemService.listMaps(queryWrapper))){
            return new CommonResult<>(404, "查询失败");
        }
        return new CommonResult<>(200, "查询成功",itemService.listMaps(queryWrapper));
    }

    @Operation(summary = "根据商品查找数量")
    @GetMapping("/searchItemCount/{itemName}")
    public int searchItemCount(@PathVariable String itemName){
        return itemService.getOne(new QueryWrapper<Item>()
                .eq("item_name", itemName)
                .select("item_amount"))
                .getItemAmount();
    }

    @Operation(summary = "根据商品名查找")
    @GetMapping("/search/{item_name}")
    public Item search(@PathVariable String item_name) {
        return itemService.getOne(new QueryWrapper<Item>().like("item_name", item_name));
    }


    @Operation(summary = "根据城市名查找订单")
    @GetMapping("/searchByCity/{item_city}")
    public Item searchByCity(@PathVariable String item_city) {
        return itemService.getOne(new QueryWrapper<Item>().eq("item_city", item_city));
    }

    @PostMapping("/decreaseAmount")
    @Operation(summary = "减少商品数量")
    @Transactional(rollbackFor = Exception.class)
    public void decreaseAmount(String itemName){
        int count = itemService.getOne(new QueryWrapper<Item>().eq("item_name", itemName)).getItemAmount();
        if (count == 0){
            log.error("库存不足");
            throw new RuntimeException("库存不足");
        }
        itemService.update(new UpdateWrapper<Item>()
                .eq("item_name", itemName)
                .set("item_amount", count - 1));
    }

    @PostMapping("/addShopCard/{username}")
    @Operation(summary = "添加购物车")
    public CommonResult<String> addShopCard(@RequestBody Item item,@PathVariable String username){
        if (redisUtil.hasHashKey("Item:"+ username, item.getItemName())){
            return new CommonResult<>(409, "已添加");
        }
        redisUtil.setHash("Item:"+ username, item.getItemName(), JSONUtil.toJsonStr(item));
        return new CommonResult<>(200, "添加成功");
    }

    @GetMapping("/getShopCard/{username}")
    @Operation(summary = "获取购物车")
    public CommonResult<List<Item>> getShopCard(@PathVariable String username){
        if (!redisUtil.hasKey("Item:" + username)){
            return new CommonResult<>(400, "购物车为空");
        }
        Map<Object, Object> hashKeys = redisUtil.getHashKeys("Item:" + username);
        List<Item> list = hashKeys.values().stream().map(v -> JSONUtil.parseObj(v).toBean(Item.class)).toList();
        return new CommonResult<>(200, "查询成功",list);
    }


    @PostMapping("/deleteShopCard/{username}")
    @Operation(summary = "删除购物车")
    public CommonResult<String> deleteShopCard(@RequestBody Item item,@PathVariable String username){
        redisUtil.deleteHash("Item:" + username,item.getItemName());
        return new CommonResult<>(200, "删除成功");
    }
}
