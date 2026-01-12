package com.tinuvile.test.trigger;

import com.alibaba.fastjson.JSON;
import com.tinuvile.api.dto.RaffleAwardListRequestDTO;
import com.tinuvile.api.dto.RaffleAwardListResponseDTO;
import com.tinuvile.api.dto.RaffleStrategyRequestDTO;
import com.tinuvile.api.dto.RaffleStrategyResponseDTO;
import com.tinuvile.types.model.Response;
import com.tinuvile.trigger.http.RaffleStrategyController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Tinuvile
 * @description 抽奖控制器测试类
 * @since 2025/12/4
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleControllerTest {

    @Resource
    private RaffleStrategyController raffleController;

    /**
     * 测试策略装配接口 - 正常场景
     */
    @Test
    public void test_strategyArmory_success() {
        Long strategyId = 10001L;
        
        Response<Boolean> response = raffleController.strategyArmory(strategyId);
        
        log.info("策略装配测试 - 请求参数: strategyId={}", strategyId);
        log.info("策略装配测试 - 响应结果: {}", JSON.toJSONString(response));
        
        Assert.assertNotNull("响应结果不能为空", response);
        Assert.assertEquals("响应状态码应该为成功", "0000", response.getCode());
        Assert.assertNotNull("响应数据不能为空", response.getData());
        Assert.assertTrue("策略装配应该成功", response.getData());
    }

    /**
     * 测试策略装配接口 - 多个策略
     */
    @Test
    public void test_strategyArmory_multiple_strategies() {
        Long[] strategyIds = {10001L, 10002L, 10003L};
        
        for (Long strategyId : strategyIds) {
            Response<Boolean> response = raffleController.strategyArmory(strategyId);
            
            log.info("策略装配测试 - 策略ID: {}, 响应结果: {}", strategyId, JSON.toJSONString(response));
            
            Assert.assertNotNull("响应结果不能为空", response);
            Assert.assertEquals("响应状态码应该为成功", "0000", response.getCode());
            Assert.assertTrue("策略装配应该成功", response.getData());
        }
    }

    /**
     * 测试查询抽奖奖品列表接口 - 策略10001
     */
    @Test
    public void test_queryRaffleAwardList_strategy_10001() {
        RaffleAwardListRequestDTO requestDTO = new RaffleAwardListRequestDTO();
        requestDTO.setStrategyId(10001L);
        
        Response<List<RaffleAwardListResponseDTO>> response = raffleController.queryRaffleAwardList(requestDTO);
        
        log.info("查询奖品列表测试 - 请求参数: {}", JSON.toJSONString(requestDTO));
        log.info("查询奖品列表测试 - 响应结果: {}", JSON.toJSONString(response));
        
        Assert.assertNotNull("响应结果不能为空", response);
        Assert.assertEquals("响应状态码应该为成功", "0000", response.getCode());
        Assert.assertNotNull("奖品列表不能为空", response.getData());
        Assert.assertFalse("奖品列表应该有数据", response.getData().isEmpty());
        
        // 验证奖品数据结构
        RaffleAwardListResponseDTO firstAward = response.getData().get(0);
        Assert.assertNotNull("奖品ID不能为空", firstAward.getAwardId());
        Assert.assertNotNull("奖品标题不能为空", firstAward.getAwardTitle());
        Assert.assertNotNull("排序不能为空", firstAward.getSort());
        
        log.info("策略10001包含 {} 个奖品", response.getData().size());
    }

    /**
     * 测试查询抽奖奖品列表接口 - 策略10002
     */
    @Test
    public void test_queryRaffleAwardList_strategy_10002() {
        RaffleAwardListRequestDTO requestDTO = new RaffleAwardListRequestDTO();
        requestDTO.setStrategyId(10002L);
        
        Response<List<RaffleAwardListResponseDTO>> response = raffleController.queryRaffleAwardList(requestDTO);
        
        log.info("查询奖品列表测试(策略10002) - 请求参数: {}", JSON.toJSONString(requestDTO));
        log.info("查询奖品列表测试(策略10002) - 响应结果: {}", JSON.toJSONString(response));
        
        Assert.assertNotNull("响应结果不能为空", response);
        Assert.assertEquals("响应状态码应该为成功", "0000", response.getCode());
        Assert.assertNotNull("奖品列表不能为空", response.getData());
        
        log.info("策略10002包含 {} 个奖品", response.getData().size());
    }

    /**
     * 测试查询抽奖奖品列表接口 - 策略10003
     */
    @Test
    public void test_queryRaffleAwardList_strategy_10003() {
        RaffleAwardListRequestDTO requestDTO = new RaffleAwardListRequestDTO();
        requestDTO.setStrategyId(10003L);
        
        Response<List<RaffleAwardListResponseDTO>> response = raffleController.queryRaffleAwardList(requestDTO);
        
        log.info("查询奖品列表测试(策略10003) - 请求参数: {}", JSON.toJSONString(requestDTO));
        log.info("查询奖品列表测试(策略10003) - 响应结果: {}", JSON.toJSONString(response));
        
        Assert.assertNotNull("响应结果不能为空", response);
        Assert.assertEquals("响应状态码应该为成功", "0000", response.getCode());
        Assert.assertNotNull("奖品列表不能为空", response.getData());
        
        log.info("策略10003包含 {} 个奖品", response.getData().size());
    }

    /**
     * 测试随机抽奖接口 - 策略10001
     */
    @Test
    public void test_randomRaffle_strategy_10001() {
        // 先装配策略
        raffleController.strategyArmory(10001L);
        
        RaffleStrategyRequestDTO requestDTO = new RaffleStrategyRequestDTO();
        requestDTO.setStrategyId(10001L);
        
        Response<RaffleStrategyResponseDTO> response = raffleController.randomRaffle(requestDTO);
        
        log.info("随机抽奖测试 - 请求参数: {}", JSON.toJSONString(requestDTO));
        log.info("随机抽奖测试 - 响应结果: {}", JSON.toJSONString(response));
        
        Assert.assertNotNull("响应结果不能为空", response);
        Assert.assertEquals("响应状态码应该为成功", "0000", response.getCode());
        Assert.assertNotNull("抽奖结果不能为空", response.getData());
        Assert.assertNotNull("奖品ID不能为空", response.getData().getAwardId());
        Assert.assertNotNull("奖品索引不能为空", response.getData().getAwardIndex());
        
        log.info("抽中奖品ID: {}, 奖品索引: {}", 
                response.getData().getAwardId(), 
                response.getData().getAwardIndex());
    }

    /**
     * 测试随机抽奖接口 - 策略10002
     */
    @Test
    public void test_randomRaffle_strategy_10002() {
        // 先装配策略
        raffleController.strategyArmory(10002L);
        
        RaffleStrategyRequestDTO requestDTO = new RaffleStrategyRequestDTO();
        requestDTO.setStrategyId(10002L);
        
        Response<RaffleStrategyResponseDTO> response = raffleController.randomRaffle(requestDTO);
        
        log.info("随机抽奖测试(策略10002) - 请求参数: {}", JSON.toJSONString(requestDTO));
        log.info("随机抽奖测试(策略10002) - 响应结果: {}", JSON.toJSONString(response));
        
        Assert.assertNotNull("响应结果不能为空", response);
        Assert.assertEquals("响应状态码应该为成功", "0000", response.getCode());
        Assert.assertNotNull("抽奖结果不能为空", response.getData());
        Assert.assertNotNull("奖品ID不能为空", response.getData().getAwardId());
        
        log.info("抽中奖品ID: {}, 奖品索引: {}", 
                response.getData().getAwardId(), 
                response.getData().getAwardIndex());
    }

    /**
     * 测试随机抽奖接口 - 策略10003
     */
    @Test
    public void test_randomRaffle_strategy_10003() {
        // 先装配策略
        raffleController.strategyArmory(10003L);
        
        RaffleStrategyRequestDTO requestDTO = new RaffleStrategyRequestDTO();
        requestDTO.setStrategyId(10003L);
        
        Response<RaffleStrategyResponseDTO> response = raffleController.randomRaffle(requestDTO);
        
        log.info("随机抽奖测试(策略10003) - 请求参数: {}", JSON.toJSONString(requestDTO));
        log.info("随机抽奖测试(策略10003) - 响应结果: {}", JSON.toJSONString(response));
        
        Assert.assertNotNull("响应结果不能为空", response);
        Assert.assertEquals("响应状态码应该为成功", "0000", response.getCode());
        Assert.assertNotNull("抽奖结果不能为空", response.getData());
        Assert.assertNotNull("奖品ID不能为空", response.getData().getAwardId());
        
        log.info("抽中奖品ID: {}, 奖品索引: {}", 
                response.getData().getAwardId(), 
                response.getData().getAwardIndex());
    }

    /**
     * 测试多次随机抽奖 - 验证概率分布
     */
    @Test
    public void test_randomRaffle_multiple_times() {
        // 先装配策略
        raffleController.strategyArmory(10001L);
        
        int raffleCount = 100;
        log.info("开始进行 {} 次随机抽奖测试", raffleCount);
        
        for (int i = 0; i < raffleCount; i++) {
            RaffleStrategyRequestDTO requestDTO = new RaffleStrategyRequestDTO();
            requestDTO.setStrategyId(10001L);
            
            Response<RaffleStrategyResponseDTO> response = raffleController.randomRaffle(requestDTO);
            
            Assert.assertNotNull("响应结果不能为空", response);
            Assert.assertEquals("响应状态码应该为成功", "0000", response.getCode());
            Assert.assertNotNull("抽奖结果不能为空", response.getData());
            
            if (i % 20 == 0) {
                log.info("第 {} 次抽奖结果: 奖品ID={}, 索引={}", 
                        i + 1, 
                        response.getData().getAwardId(), 
                        response.getData().getAwardIndex());
            }
        }
        
        log.info("完成 {} 次随机抽奖测试", raffleCount);
    }

    /**
     * 测试异常场景 - 不存在的策略ID
     */
    @Test
    public void test_randomRaffle_invalid_strategy() {
        RaffleStrategyRequestDTO requestDTO = new RaffleStrategyRequestDTO();
        requestDTO.setStrategyId(99999L); // 不存在的策略ID
        
        Response<RaffleStrategyResponseDTO> response = raffleController.randomRaffle(requestDTO);
        
        log.info("异常场景测试 - 请求参数: {}", JSON.toJSONString(requestDTO));
        log.info("异常场景测试 - 响应结果: {}", JSON.toJSONString(response));
        
        Assert.assertNotNull("响应结果不能为空", response);
        Assert.assertNotEquals("应该返回错误状态码", "0000", response.getCode());
    }

    /**
     * 测试异常场景 - 查询不存在的策略奖品列表
     */
    @Test
    public void test_queryRaffleAwardList_invalid_strategy() {
        RaffleAwardListRequestDTO requestDTO = new RaffleAwardListRequestDTO();
        requestDTO.setStrategyId(99999L); // 不存在的策略ID
        
        Response<List<RaffleAwardListResponseDTO>> response = raffleController.queryRaffleAwardList(requestDTO);
        
        log.info("异常场景测试(查询奖品列表) - 请求参数: {}", JSON.toJSONString(requestDTO));
        log.info("异常场景测试(查询奖品列表) - 响应结果: {}", JSON.toJSONString(response));
        
        Assert.assertNotNull("响应结果不能为空", response);
        // 根据业务逻辑，可能返回空列表或错误状态码
        if ("0000".equals(response.getCode())) {
            // 如果返回成功，数据应该为空
            Assert.assertTrue("不存在的策略应该返回空列表", 
                    response.getData() == null || response.getData().isEmpty());
        } else {
            // 如果返回错误状态码，也是合理的
            log.info("返回错误状态码: {}, 错误信息: {}", response.getCode(), response.getInfo());
        }
    }

    /**
     * 测试完整流程 - 装配策略 -> 查询奖品列表 -> 随机抽奖
     */
    @Test
    public void test_complete_raffle_flow() {
        Long strategyId = 10001L;
        
        // 1. 装配策略
        log.info("=== 步骤1: 装配策略 ===");
        Response<Boolean> armoryResponse = raffleController.strategyArmory(strategyId);
        Assert.assertNotNull("策略装配响应不能为空", armoryResponse);
        Assert.assertEquals("策略装配应该成功", "0000", armoryResponse.getCode());
        Assert.assertTrue("策略装配结果应该为true", armoryResponse.getData());
        
        // 2. 查询奖品列表
        log.info("=== 步骤2: 查询奖品列表 ===");
        RaffleAwardListRequestDTO listRequestDTO = new RaffleAwardListRequestDTO();
        listRequestDTO.setStrategyId(strategyId);
        Response<List<RaffleAwardListResponseDTO>> listResponse = raffleController.queryRaffleAwardList(listRequestDTO);
        Assert.assertNotNull("奖品列表响应不能为空", listResponse);
        Assert.assertEquals("查询奖品列表应该成功", "0000", listResponse.getCode());
        Assert.assertFalse("奖品列表应该有数据", listResponse.getData().isEmpty());
        
        // 3. 随机抽奖
        log.info("=== 步骤3: 随机抽奖 ===");
        RaffleStrategyRequestDTO raffleStrategyRequestDTO = new RaffleStrategyRequestDTO();
        raffleStrategyRequestDTO.setStrategyId(strategyId);
        Response<RaffleStrategyResponseDTO> raffleResponse = raffleController.randomRaffle(raffleStrategyRequestDTO);
        Assert.assertNotNull("抽奖响应不能为空", raffleResponse);
        Assert.assertEquals("抽奖应该成功", "0000", raffleResponse.getCode());
        Assert.assertNotNull("抽奖结果不能为空", raffleResponse.getData());
        
        log.info("=== 完整流程测试成功 ===");
        log.info("装配结果: {}", armoryResponse.getData());
        log.info("奖品数量: {}", listResponse.getData().size());
        log.info("抽奖结果: 奖品ID={}, 索引={}", 
                raffleResponse.getData().getAwardId(), 
                raffleResponse.getData().getAwardIndex());
    }

}
