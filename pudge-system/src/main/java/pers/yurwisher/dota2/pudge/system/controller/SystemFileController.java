package pers.yurwisher.dota2.pudge.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.system.pojo.SystemFileUploadBack;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemFileQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemFileTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemFileVo;
import pers.yurwisher.dota2.pudge.system.service.ISystemFileService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yq
 * @date 2020-12-31 17:12:48
 * @description 系统文件
 * @since V1.0.0
 */
@RestController
@RequestMapping("/file")
public class SystemFileController extends BaseController {
    private final ISystemFileService systemFileService;

    public SystemFileController(ISystemFileService systemFileService) {
        this.systemFileService = systemFileService;
    }

    @GetMapping
    public R<PageR<SystemFileTo>> list(@ModelAttribute SystemFileQo qo) {
        return R.ok(systemFileService.list(qo));
    }

    @PostMapping("upload")
    public R<SystemFileUploadBack> upload(@RequestParam("file") MultipartFile file,
                                          @RequestParam("fileTag") Integer fileTag,
                                          @RequestParam("mimeType") String mimeType,
                                          HttpServletRequest request) {
        return R.ok(systemFileService.upload(file, fileTag, mimeType,request));
    }

    @GetMapping("{entityId}")
    public R<List<SystemFileVo>> getEntityFiles(@PathVariable Long entityId) {
        return R.ok(systemFileService.getEntityFiles(entityId));
    }

    @GetMapping("download/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) {
        systemFileService.download(id,response);
    }
}
