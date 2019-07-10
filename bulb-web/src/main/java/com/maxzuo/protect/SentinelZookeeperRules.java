package com.maxzuo.protect;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Sentinel使用Zookeeper配置动态规则（Push模式）
 * <p>
 * Created by zfh on 2019/07/05
 */
@Component
public class SentinelZookeeperRules {

    @Value("${zookeeper.address}")
    private String zookeeperAddress;

    private static final String FLOW_RULE_PATH = "/sentinel_rules/%s/flow";

    private static final String DEGRADE_RULE_PATH = "/sentinel_rules/%s/degrade";

    @PostConstruct
    public void init() {
        System.out.println("【Sentinel保护资源】开始检出zookeeper规则 ...");
        String appName = System.getProperty("appname");
        // 限流规则-Qos
        String flowPath = String.format(FLOW_RULE_PATH, appName);
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ZookeeperDataSource<>(zookeeperAddress, flowPath,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

        // 降级规则-RT
        String degradePath = String.format(DEGRADE_RULE_PATH, appName);
        ReadableDataSource<String, List<DegradeRule>> degradeDataSource = new ZookeeperDataSource<>(zookeeperAddress, degradePath,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {}));
        DegradeRuleManager.register2Property(degradeDataSource.getProperty());
    }
}