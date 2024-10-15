package com.swpu.contactserver.contact.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swpu.contactserver.common.result.Result;
import com.swpu.contactserver.contact.dto.ContactDTO;
import com.swpu.contactserver.contact.dto.ContactPageQueryDTO;
import com.swpu.contactserver.contact.entity.Contact;
import com.swpu.contactserver.contact.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contact")
@Slf4j
public class ContactController {
    @Autowired
    private ContactService contactService;
    @PostMapping("/add")
    public Result<?> addContact(@RequestBody ContactDTO contactDTO){
        log.info("添加联系人：:{}", contactDTO);
        contactService.add(contactDTO);
        return new Result<>().success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Contact contact){
        log.info("编辑联系人信息：{}",contact);
        contactService.updateContact(contact);
        return new Result<>().success();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id){
        log.info("删除联系人:{}",id);
        contactService.delete(id);
        return new Result<>().success();
    }
    @GetMapping("/page")
    public Result<?> page(ContactPageQueryDTO contactPageQueryDTO){
        log.info("分页查询对象：{}",contactPageQueryDTO);
        Page<Contact> page = contactService.getPageContact(contactPageQueryDTO);
        JSONObject obj = new JSONObject();
        obj.put("total",page.getTotal());
        obj.put("rows",page.getRecords());
        return new Result<>().success().put(obj);
    }

}
