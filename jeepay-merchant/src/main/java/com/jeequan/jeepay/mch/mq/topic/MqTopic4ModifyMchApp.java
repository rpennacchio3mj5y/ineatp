/*
 * Copyright (c) 2021-2031, 河北计全科技有限公司 (https://www.jeequan.com & jeequan@126.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeequan.jeepay.mch.mq.topic;

import com.alibaba.fastjson.JSONObject;
import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.core.utils.JsonKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/*
* 更改商户应用信息
*
* @author terrfly
* @site https://www.jeepay.vip
* @date 2021/6/8 17:10
*/
@Slf4j
@Component
public class MqTopic4ModifyMchApp extends ActiveMQTopic{

    @Autowired private JmsTemplate jmsTemplate;

    public MqTopic4ModifyMchApp(){
        super(CS.MQ.TOPIC_MODIFY_MCH_APP);
    }

    /** 推送消息到各个节点 **/
    public void push(String mchNo, String appId) {
        JSONObject jsonObject = JsonKit.newJson("mchNo", mchNo);
        jsonObject.put("appId", appId);

        this.jmsTemplate.convertAndSend(this, jsonObject.toString());
    }

}
