package com.buyi.controller;

import com.buyi.common.PageResult;
import com.buyi.common.Result;
import com.buyi.entity.Apply;
import com.buyi.entity.User;
import com.buyi.exception.BizException;
import com.buyi.service.ApplyService;
import com.buyi.utils.MyUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply")
public class ApplyController {

    @Resource
    private ApplyService applyService;

    @GetMapping("/findAll")
    public Result<List<Apply>> findAll() {
        List<Apply> applyList = applyService.findAll();
        return Result.ok(applyList);
    }

    @GetMapping("/page")
    public Result<PageResult> pageQuery(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        PageResult pageResult = applyService.pageQuery(page, size);
        return Result.ok(pageResult);
    }

    @GetMapping("/pageOne")
    public Result<PageResult> pageQueryOne(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                           HttpServletRequest request) {
        User user = MyUtils.getUser(request);
        PageResult pageResult = applyService.pageQueryOne(user.getSid(), page, size);
        return Result.ok(pageResult);
    }

    @GetMapping("/find")
    public Result<List<Apply>> find(HttpServletRequest request) {
        User user = MyUtils.getUser(request);
        List<Apply> applyList = applyService.findBySid(user.getSid());
        return Result.ok(applyList);
    }

    /**
     * 审核申请
     *
     * @param apply 申请
     * @return s
     */
    @GetMapping("/audit")
    public Result<String> audit(Apply apply) {
        applyService.audit(apply);
        return Result.ok();
    }

    /**
     * 学生撤回申请
     *
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public Result<String> delete(@PathVariable("id") Integer id) {
        applyService.deleteById(id);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result<String> update(Apply apply) {
        applyService.update(apply);
        return Result.ok();
    }

    @PostMapping("/add")
    public Result<String> save(Apply apply, HttpServletRequest request) {
        User user = MyUtils.getUser(request);
        applyService.save(user, apply);
        return Result.ok();
    }


}
