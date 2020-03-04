package com.ysq.web.app;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @date 2020/3/3 11:06
 * @content
 */
@RestController
@RequestMapping("/api")
@Api("swaggerDemoController 相关的api")
public class SwaggerDemoController {

    private final static Logger logger = LoggerFactory.getLogger(SwaggerDemoController.class);

    @ApiOperation(value = "根据id查询学生信息", notes = "查询数据库中某个的学生信息")
    @ApiImplicitParam(name = "id", value = "学生ID", paramType = "path", required = true, dataType = "int")//dataType=-"Integer"无效，在swagger中无法识别
    @RequestMapping(value = "/getStudent/{id}", method = RequestMethod.POST)
    public String getStudent(@PathVariable int id) {//@ApiParam(value = "用户Id", required = true) 这个参数放在@PathVariable前，可以代替@ApiImplicitParam
        logger.info("开始查询某个学生信息,学生id：" + id);

        String resultJson = "{\n" +
                "  \"retCode\": \"000000\",\n" +
                "  \"message\": \"成功\",\n" +
                "  \"result\": null\",\n" +
                "  \"id\": " + id + "\n" +
                "}";

        return resultJson;
    }

    @ApiOperation(value = "说明方法的用途、作用", notes = "方法的备注说明")
    @RequestMapping(value = "/getScode", method = RequestMethod.GET)
    public String getScode() {

        String resultJson = "{\n" +
                "  \"retCode\": \"000000\",\n" +
                "  \"message\": \"成功\",\n" +
                "  \"result\": {\n" +
                "    \"id\": 382334,\n" +
                "    \"name\": \"陈三月\",\n" +
                "    \"groupName\": \"健身组\",\n" +
                "    \"job\": \"电焊工\",\n" +
                "    \"verifyFace\": \"https://uat.quanroon.com/nf/userfiles345_33.jpg\",\n" +
                "    \"installType\": \"3\",\n" +
                "    \"installTypeStr\": \"出场\",\n" +
                "    \"ioTime\": 1583123223000,\n" +
                "    \"ioTimeStr\": \"12:27:03\",\n" +
                "    \"projId\": 1237,\n" +
                "    \"temperature\": \"36.6\",\n" +
                "    \"isMask\": \"0\"\n" +
                "  }\n" +
                "}";

        return resultJson;
    }
}
