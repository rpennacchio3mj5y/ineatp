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
package com.jeequan.jeepay.pay.channel.wxpay;

import com.alibaba.fastjson.JSONObject;
import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.core.model.params.wxpay.WxpayIsvParams;
import com.jeequan.jeepay.core.model.params.wxpay.WxpayNormalMchParams;
import com.jeequan.jeepay.pay.channel.IChannelUserService;
import com.jeequan.jeepay.pay.model.MchConfigContext;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/*
* 微信支付 获取微信openID实现类
*
* @author terrfly
* @site https://www.jeepay.vip
* @date 2021/6/8 17:22
*/
@Service
@Slf4j
public class WxpayChannelUserService implements IChannelUserService {

    /** 默认官方跳转地址 **/
    private static final String DEFAULT_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";

    @Override
    public String getIfCode() {
        return CS.IF_CODE.WXPAY;
    }

    @Override
    public String buildUserRedirectUrl(String callbackUrlEncode, MchConfigContext mchConfigContext) {

        String appId = null;
        String oauth2Url = "";
        if(mchConfigContext.isIsvsubMch()){
            WxpayIsvParams wxpayIsvParams = mchConfigContext.getIsvConfigContext().getIsvParamsByIfCode(CS.IF_CODE.WXPAY, WxpayIsvParams.class);
            appId = wxpayIsvParams.getAppId();
            oauth2Url = wxpayIsvParams.getOauth2Url();
        }else{
            //获取商户配置信息
            WxpayNormalMchParams normalMchParams = mchConfigContext.getNormalMchParamsByIfCode(CS.IF_CODE.WXPAY, WxpayNormalMchParams.class);
            appId = normalMchParams.getAppId();
            oauth2Url = normalMchParams.getOauth2Url();
        }

        if(StringUtils.isBlank(oauth2Url)){
            oauth2Url = DEFAULT_OAUTH_URL;
        }

        return String.format(oauth2Url + "?appid=%s&scope=snsapi_base&state=&redirect_uri=%s", appId, callbackUrlEncode);
    }

    @Override
    public String getChannelUserId(JSONObject reqParams, MchConfigContext mchConfigContext) {
        String code = reqParams.getString("code");
        try {
            return mchConfigContext.getWxServiceWrapper().getWxMpService().getOAuth2Service().getAccessToken(code).getOpenId();
        } catch (WxErrorException e) {
            e.printStackTrace();
            return null;
        }
    }

}
