package com.ysq.web.ios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysq.entity.DemoResp;
import io.swagger.annotations.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @date 2020/3/4 13:52
 * @content
 */
@RestController
@RequestMapping("/ios")
@Api(tags = "IOS标准接口")
public class ApiController {
    @Resource
    private ObjectMapper mapper;

    @PostMapping("/ps")
    @ApiOperation(value = "接受json参数", notes = "演示json参数是否接受成功")
    public String post(@ApiParam(name = "接收json参数", defaultValue = "{}")
                       @RequestBody String json) throws IOException {
        Map map = mapper.readValue(json, Map.class);
        System.out.println(map);
        return json;
    }

    /**
     * 根据电影名字获取电影
     *
     * @param fileName
     * @return
     */
    @GetMapping("/getFilms")
    @ApiOperation(value = "根据名字获取电影")
    @ApiResponses(value = {
            @ApiResponse(code = 1000, message = "成功"),
            @ApiResponse(code = 1001, message = "失败"),
            @ApiResponse(code = 1002, message = "缺少参数") })
    public DemoResp getFilmsByName(@ApiParam("电影名称") @RequestParam("fileName") String fileName) {

        if(fileName.equals("1")){
            return  DemoResp.builder().code("1000").build();
        }else{
            return  DemoResp.builder().code("1001").build();
        }
    }
}