package org.xxpay.service.channel.tencent.service;

import org.xxpay.service.channel.tencent.common.Configure;
import org.xxpay.service.channel.tencent.protocol.redpack_protocol.QueryRedpackReqData;
import org.springframework.stereotype.Service;

/**
 * User: dingzhiwei
 * Date: 2016/06/03
 * Time: 23:49
 */
@Service
public class QueryRedpackService extends BaseService {

    public QueryRedpackService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.QUERY_REDPACK_API);
    }

    /**
     * 请求红包服务
     * @param queryRedpackReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(QueryRedpackReqData queryRedpackReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(queryRedpackReqData);

        return responseString;
    }
}
