package com.buyi.controller;

import com.buyi.common.PageResult;
import com.buyi.common.Result;
import com.buyi.entity.Train;
import com.buyi.entity.User;
import com.buyi.exception.BizException;
import com.buyi.service.TrainService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {

    @Resource
    private TrainService trainService;

    @GetMapping("/findAll")
    public Result<List<Train>> findAll() {
        List<Train> trainList = trainService.findAll();
        return Result.ok(trainList);
    }

    @GetMapping("/pageAdmin")
    public Result<PageResult> pageQueryAdmin(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        PageResult pageResult = trainService.pageQuery(page, size);
        return Result.ok(pageResult);
    }

    @GetMapping("/page")
    public Result<PageResult> pageQuery(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                        HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new BizException("请先登录");
        }

        PageResult pageResult = trainService.pageQuery(user.getSid(), page, size);
        return Result.ok(pageResult);
    }


    @GetMapping("/find/{id}")
    public Result<Train> find(@PathVariable("id") Integer id) {
        Train train = trainService.findById(id);
        return Result.ok(train);
    }

    @GetMapping("/delete/{id}")
    public Result<String> delete(@PathVariable("id") Integer id) {
        trainService.deleteById(id);
        return Result.ok();
    }

    @GetMapping("/study/{id}")
    public Result<String> study(@PathVariable("id") Integer id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new BizException("请先登录");
        }
        trainService.study(user.getSid(), id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result<String> update(Train train) {
        trainService.update(train);
        return Result.ok();
    }

    @PostMapping("/publish")
    public Result<String> publish(Train train) {
        trainService.publish(train);
        return Result.ok();
    }

    //  `../train/updateStatus/${id}/${status}`
    @GetMapping("/updateStatus/{id}/{status}")
    public Result<String> updateStatus(@PathVariable("id") Integer id, @PathVariable("status") Integer status) {
        Train train = new Train();
        train.setId(id);
        train.setStatus(status);
        trainService.update(train);
        return Result.ok();
    }
}
