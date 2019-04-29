package com.jackluan.deploy.virtualMachine;

import com.jackluan.deploy.virtualMachine.entity.VMEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/vm")
public class VMController {

    @Autowired
    VMService vmService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public Object setVMInfo() {
        vmService.createVM(new VMEntity(UUID.randomUUID().toString().replaceAll("-", ""), "192.168.1.1","8888", 1 ));
        return "Success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getVMInfo(@PathVariable String id, Model model) {
        return vmService.queryVM(id).toString();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        System.out.println(vmService.queryVMByJdbc());
    }

}
