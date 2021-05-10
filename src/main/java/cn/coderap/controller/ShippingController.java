package cn.coderap.controller;

import cn.coderap.consts.NewMallConsts;
import cn.coderap.pojo.User;
import cn.coderap.pojo.bo.ShippingForm;
import cn.coderap.pojo.vo.ResponseVO;
import cn.coderap.service.IShippingService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * Created by yw
 * 2021/4/30
 */
@RestController
public class ShippingController {

    @Resource
    private IShippingService shippingService;

    @PostMapping("/shippings")
    public ResponseVO<Map<String,Integer>> add(@Valid @RequestBody ShippingForm form,
                                               HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return shippingService.add(user.getId(), form);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVO delete(@PathVariable Integer shippingId, HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return shippingService.delete(user.getId(),shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVO update(@PathVariable Integer shippingId,
                             @Valid @RequestBody ShippingForm form,
                             HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return shippingService.update(user.getId(),shippingId,form);
    }

    @GetMapping("/shippings")
    public ResponseVO<PageInfo> list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                     HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return shippingService.list(user.getId(),pageNum,pageSize);
    }
}
