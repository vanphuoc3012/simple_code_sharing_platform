package platform.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import platform.models.Code;
import platform.service.CodeService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class CodeControllerReturnView {

    @Autowired
    CodeService codeService;

    @GetMapping("/code/new")
    private String addCode() {
        return "newCode";
    }

    @GetMapping("/code/{id}")
    private ModelAndView getCode(@PathVariable String id) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(1);

        System.out.println("Getting code with id: "+id+" for View");

        Code code = codeService.clientAccessCodeById(UUID.fromString(id));
        ModelAndView modelAndView = new ModelAndView();
        if(code == null) {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        }
            modelAndView.setViewName("code");
            modelAndView.addObject("code", code);
        System.out.println(code);
        return modelAndView;
    }

    @GetMapping("/code/latest")
    private ModelAndView getCodeWithId() throws InterruptedException {
//        TimeUnit.SECONDS.sleep(1);
        System.out.println("Getting latest code for View");
        List<Code> list = codeService.findLatest();
        for(Code c : list) {
            c.setCode(c.getCode().replace("(\\r\\n|\\n|\\r)", ""));
        }
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("listCode");
            modelAndView.addObject("latestCode", list);
            return modelAndView;
    }
}
