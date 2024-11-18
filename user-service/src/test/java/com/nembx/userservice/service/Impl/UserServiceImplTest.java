package com.nembx.userservice.service.Impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nembx.userservice.domian.User;
import com.nembx.userservice.mapper.UserMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#produceCode()}
     */
    @Test
    void testProduceCode() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@396c7990 testClass = com.nembx.userservice.service.Impl.DiffblueFakeClass641, locations = [], classes = [com.nembx.userservice.service.Impl.UserServiceImpl], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@7f40cbfa, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@367eb273, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@a4379839, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@49a02dc4], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:1002)
        //       at java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:150)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:647)
        //   See https://diff.blue/R026 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by produceCode()
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        (new UserServiceImpl()).produceCode();
    }

    /**
     * Method under test: {@link UserServiceImpl#produceCode()}
     */
    @Test
    void testProduceCode2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@396c7990 testClass = com.nembx.userservice.service.Impl.DiffblueFakeClass641, locations = [], classes = [com.nembx.userservice.service.Impl.UserServiceImpl], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@7f40cbfa, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@367eb273, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@a4379839, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@49a02dc4], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:1002)
        //       at java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:150)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:647)
        //   See https://diff.blue/R026 to resolve this issue.

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setId((String) any());
        doNothing().when(user).setImage((String) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setUsername((String) any());
        user.setEmail("jane.doe@example.org");
        user.setId("42");
        user.setImage("1234567890abcdefghijklmnopqrstuvwxwzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.createToken(user);
        userServiceImpl.produceCode();
        verify(user, atLeast(1)).getUsername();
        verify(user).setEmail((String) any());
        verify(user).setId((String) any());
        verify(user).setImage((String) any());
        verify(user).setPassword((String) any());
        verify(user).setUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#sendEmail(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSendEmail() {
        // TODO: Complete this test.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@fb49ae5 testClass = com.nembx.userservice.service.Impl.DiffblueFakeClass731, locations = [], classes = [com.nembx.userservice.service.Impl.UserServiceImpl], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@7f40cbfa, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@367eb273, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@a4379839, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@49a02dc4], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:1002)
        //       at java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:150)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:647)
        //   See https://diff.blue/R026 to resolve this issue.

        userServiceImpl.sendEmail("jane.doe@example.org");
    }

    /**
     * Method under test: {@link UserServiceImpl#createToken(User)}
     */
    @Test
    void testCreateToken() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@70bea81d testClass = com.nembx.userservice.service.Impl.DiffblueFakeClass3, locations = [], classes = [com.nembx.userservice.service.Impl.UserServiceImpl, com.nembx.userservice.util.RedisUtil, org.springframework.data.redis.core.StringRedisTemplate], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@7f40cbfa, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@367eb273, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@a4379839, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@49a02dc4], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:1002)
        //       at java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:150)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:647)
        //   See https://diff.blue/R026 to resolve this issue.

        UserServiceImpl userServiceImpl = new UserServiceImpl();

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId("42");
        user.setImage("Image");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        assertFalse(userServiceImpl.createToken(user));
    }

    /**
     * Method under test: {@link UserServiceImpl#createToken(User)}
     */
    @Test
    void testCreateToken2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@70bea81d testClass = com.nembx.userservice.service.Impl.DiffblueFakeClass3, locations = [], classes = [com.nembx.userservice.service.Impl.UserServiceImpl, com.nembx.userservice.util.RedisUtil, org.springframework.data.redis.core.StringRedisTemplate], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@7f40cbfa, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@367eb273, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@a4379839, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@49a02dc4], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:1002)
        //       at java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:150)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:647)
        //   See https://diff.blue/R026 to resolve this issue.

        UserServiceImpl userServiceImpl = new UserServiceImpl();
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setId((String) any());
        doNothing().when(user).setImage((String) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setUsername((String) any());
        user.setEmail("jane.doe@example.org");
        user.setId("42");
        user.setImage("Image");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        assertFalse(userServiceImpl.createToken(user));
        verify(user, atLeast(1)).getUsername();
        verify(user).setEmail((String) any());
        verify(user).setId((String) any());
        verify(user).setImage((String) any());
        verify(user).setPassword((String) any());
        verify(user).setUsername((String) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#findToken(User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindToken() {
        // TODO: Complete this test.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@3eac28fa testClass = com.nembx.userservice.service.Impl.DiffblueFakeClass322, locations = [], classes = [com.nembx.userservice.service.Impl.UserServiceImpl], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@7f40cbfa, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@367eb273, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@a4379839, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@49a02dc4], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:1002)
        //       at java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:150)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:647)
        //   See https://diff.blue/R026 to resolve this issue.

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId("42");
        user.setImage("Image");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        userServiceImpl.findToken(user);
    }

    /**
     * Method under test: {@link UserServiceImpl#refreshToken(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRefreshToken() {
        // TODO: Complete this test.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@37c99791 testClass = com.nembx.userservice.service.Impl.DiffblueFakeClass644, locations = [], classes = [com.nembx.userservice.service.Impl.UserServiceImpl], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@7f40cbfa, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@367eb273, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@a4379839, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@49a02dc4], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.Spliterators$ArraySpliterator.tryAdvance(Spliterators.java:1002)
        //       at java.util.stream.ReferencePipeline.forEachWithCancel(ReferencePipeline.java:129)
        //       at java.util.stream.AbstractPipeline.copyIntoWithCancel(AbstractPipeline.java:527)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:513)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.FindOps$FindOp.evaluateSequential(FindOps.java:150)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.findFirst(ReferencePipeline.java:647)
        //   See https://diff.blue/R026 to resolve this issue.

        userServiceImpl.refreshToken("janedoe");
    }
}

