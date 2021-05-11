package cn.coderap.controller;

import cn.coderap.consts.NewMallConsts;
import cn.coderap.pojo.User;
import cn.coderap.pojo.bo.OrderCreateForm;
import cn.coderap.pojo.vo.OrderVO;
import cn.coderap.pojo.vo.ResponseVO;
import cn.coderap.service.IOrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by yw
 * 2021/5/11
 */
@RestController
public class OrderController {

    @Resource
    private IOrderService orderService;

    @PostMapping("/orders")
    public ResponseVO<OrderVO> create(@Valid @RequestBody OrderCreateForm form,
                                      HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return orderService.create(user.getId(),form.getShippingId());
    }

    @GetMapping("/orders")
    public ResponseVO<PageInfo> list(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                     HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return orderService.list(user.getId(),pageNum,pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVO<OrderVO> detail(@PathVariable Long orderNo, HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return orderService.detail(user.getId(),orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVO cancel(@PathVariable Long orderNo, HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return orderService.cancel(user.getId(),orderNo);
    }
}
