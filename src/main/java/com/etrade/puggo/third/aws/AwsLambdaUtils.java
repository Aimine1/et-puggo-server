package com.etrade.puggo.third.aws;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.FunctionConfiguration;
import software.amazon.awssdk.services.lambda.model.GetFunctionRequest;
import software.amazon.awssdk.services.lambda.model.GetFunctionResponse;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.lambda.model.LambdaException;
import software.amazon.awssdk.services.lambda.model.ListFunctionsResponse;

/**
 * @author niuzhenyu
 * @description : Aws lambda
 * @date 2024/1/15 21:13
 **/
@Slf4j
public class AwsLambdaUtils {

    private static final Region US_WEST_1 = Region.US_WEST_1;


    public static void invokeFunction(String functionName, JSONObject jsonObj) {

        InvokeResponse res;
        try {
            String json = jsonObj.toString();
            SdkBytes payload = SdkBytes.fromUtf8String(json);

            LambdaClient awsLambda = getLambdaClient();

            InvokeRequest request = InvokeRequest.builder()
                .functionName(functionName)
                .payload(payload)
                .build();

            res = awsLambda.invoke(request);
            String value = res.payload().asUtf8String();

            System.out.println(value);

        } catch (LambdaException e) {

            log.error("Aws lambda invokeFunction error", e);

        }
    }


    public static void listFunctions() {
        try {
            LambdaClient awsLambda = getLambdaClient();
            ListFunctionsResponse functionResult = awsLambda.listFunctions();
            List<FunctionConfiguration> list = functionResult.functions();
            for (FunctionConfiguration config : list) {
                System.out.println("The function name is " + config.functionName());
            }

        } catch (LambdaException e) {
            log.error("Aws lambda listFunctions error", e);
        }
    }


    public static void getFunction(String functionName) {
        try {
            LambdaClient awsLambda = getLambdaClient();

            GetFunctionRequest functionRequest = GetFunctionRequest.builder()
                .functionName(functionName)
                .build();

            GetFunctionResponse response = awsLambda.getFunction(functionRequest);
            System.out.println("The runtime of this Lambda function is " + response.configuration().runtime());

        } catch (LambdaException e) {
            log.error("Aws lambda getFunction error", e);

        }
    }


    private static LambdaClient getLambdaClient() {
        return LambdaClient.builder().region(US_WEST_1).build();
    }


    public static void main(String[] args) {
        listFunctions();
        invokeFunction("list_payment_methods", null);
    }
}
