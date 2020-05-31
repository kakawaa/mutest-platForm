package com.mutest.model.interfaces;

import lombok.Data;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/7/16 19:32
 * @description
 * @modify
 */
@Data
public class CaseResult {
    private int id;
    private int caseId;
    private String executeId;
    private String interfaceName;
    private String caseRequest;
    private String headerData;
    private String bodyData;
    private String caseResponse;
    private String caseAssertion;
    private String assertionDetail;
    private String create_time;
    private String modify_time;
}
