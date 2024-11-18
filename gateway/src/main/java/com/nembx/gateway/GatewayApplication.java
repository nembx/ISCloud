package com.nembx.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Lian
 */
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        System.out.println("gateway启动!\n" +
                "*       *  ********      *       *       ********)  *       *\n" +
                "* *     *  *            * *     * *      ********)    *   *  \n" +
                "*   *   *  ********    *   *   *   *     *              *    \n" +
                "*     * *  *          *     * *     *    ********)    *   *  \n" +
                "*       *  ********  *       *       *   ********)  *       *\n");
    }
}
