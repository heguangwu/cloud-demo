package cloud.heguangwu.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(properties = "spring.cloud.nacos.config.import-check.enabled=false")
class JacksonConfigTest {

    @Data // 简化 getter/setter，需引入 lombok
    public static class TestEntity {
        private Long id;               // 包装类型 Long
        private long count;            // 基本类型 long
        private Date createDate;       // 传统 Date 类型
        private LocalDateTime updateTime; // Java 8 时间类型
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLongSerializeString() throws Exception {
        // 1. 准备测试数据
        TestEntity entity = new TestEntity();
        entity.setId(123456789012345L);
        entity.setCount(9876543210L);

        // 2. 序列化
        String json = objectMapper.writeValueAsString(entity);
        log.info("=======>{}", json);

        // 3. 断言：Long/long 被序列化为字符串
        assertThat(json).contains("\"id\":\"123456789012345\"")
                .contains("\"count\":\"9876543210\"");
    }

    @Test
    void testDateSerialize() throws Exception {
        // 1. 准备测试数据
        TestEntity entity = new TestEntity();
        Date date = Date.from(LocalDateTime.of(2025, 12, 3, 15, 30, 0)
                .atZone(ZoneId.of("Asia/Shanghai")).toInstant());
        entity.setCreateDate(date);
        entity.setUpdateTime(LocalDateTime.of(2025, 12, 3, 15, 30, 0));

        // 2. 序列化
        String json = objectMapper.writeValueAsString(entity);
        log.info("Date and LocalDateTime=======>{}", json);

        // 3. 断言：Date 和 LocalDateTime 格式为 yyyy-MM-dd HH:mm:ss
        assertThat(json).contains("\"createDate\":\"2025-12-03 15:30:00\"")
                .contains("\"updateTime\":\"2025-12-03 15:30:00\"");
    }

    @Test
    void testDeserializeString2Long() throws Exception {
        // 1. 准备 JSON（Long 字段为字符串形式）
        String json = "{\"id\":\"123456\",\"count\":\"987654\"}";

        // 2. 反序列化
        TestEntity entity = objectMapper.readValue(json, TestEntity.class);
        log.info("entity deserialize=======>{}", entity);

        // 3. 断言：字符串成功转为 Long/long
        assertThat(entity.getId()).isEqualTo(123456L);
        assertThat(entity.getCount()).isEqualTo(987654L);
    }

    @Test
    void testDeserializeUnknown() throws Exception {
        // 1. 准备 JSON（包含未知字段 "extraField"）
        String json = "{\"id\":123,\"extraField\":\"未知内容\"}";

        // 2. 反序列化（若未关闭 FAIL_ON_UNKNOWN_PROPERTIES 会抛异常）
        TestEntity entity = objectMapper.readValue(json, TestEntity.class);

        // 3. 断言：反序列化成功，忽略未知字段
        assertThat(entity.getId()).isEqualTo(123L);
    }
}
