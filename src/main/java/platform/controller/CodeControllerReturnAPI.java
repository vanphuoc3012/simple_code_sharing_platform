package platform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.models.Code;
import platform.service.CodeService;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class CodeControllerReturnAPI {


    @Autowired
    CodeService codeService;


//    @GetMapping("/api/code")
//    public ResponseEntity getCodeAPI() {
//        Code code1 = new Code();
//        code1.setCode(code.getCode().replace("(\\r\\n|\\n|\\r)", ""));
//        code1.setDate(LocalDateTime.parse(code.getDate()));
//        return new ResponseEntity(code1, HttpStatus.OK);
//    }

    @GetMapping("/api/code/{id}")
    public ResponseEntity getCodeAPI(@PathVariable String id) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(1);
        System.out.println("code for api: "+id);
        Code code1 = codeService.clientAccessCodeById(UUID.fromString(id));
        if(code1 == null) {
            System.out.println("code not found uuid :"+id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("Found code with id: "+id+" for API");
            System.out.println(code1);
            code1.setCode(code1.getCode().replace("(\\r\\n|\\n|\\r)", ""));
            return new ResponseEntity(code1, HttpStatus.OK);
        }
    }

    @GetMapping("/api/code/latest")
//    @JsonView(View.CodeAndDate.class)
    public ResponseEntity getLatestCode() throws JsonProcessingException, InterruptedException {
//        TimeUnit.SECONDS.sleep(1);
        System.out.println("Getting latest code for API");
        List<Code> list = codeService.findLatest();
        for(Code c : list) {
            c.setCode(c.getCode().replace("(\\r\\n|\\n|\\r)", ""));
            c.setTime(0);
            c.setViews(0);
        }

        return new ResponseEntity(list, HttpStatus.OK);
    }


    @PostMapping("/api/code/new")
    public ResponseEntity addNewCodeJ(@RequestBody Code codeToAdd) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        Code codeNew = codeService.clientAddNewCode(codeToAdd);
        System.out.println(codeNew);
        return new ResponseEntity(codeNew.idReturnJson(), HttpStatus.OK);
    }

}
