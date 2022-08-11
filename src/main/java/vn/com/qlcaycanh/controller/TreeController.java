package vn.com.qlcaycanh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import vn.com.qlcaycanh.bean.ResponseResult;
import vn.com.qlcaycanh.common.MyException;
import vn.com.qlcaycanh.entity.Provider;
import vn.com.qlcaycanh.entity.Tree;
import vn.com.qlcaycanh.i18n.IMessage;
import vn.com.qlcaycanh.javabean.TreeSearchCondition;
import vn.com.qlcaycanh.service.TreeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Scope("session")
public class TreeController extends AbstractController {

    private TreeSearchCondition searchCondition;

    @Autowired
    private TreeService treeService;

    @RequestMapping(value = "/nhom-cay/tat-ca/0", method = RequestMethod.GET)
    public String init() {
        super.initController();
        searchCondition = new TreeSearchCondition();
        return "redirect:/nhom-cay/tat-ca";
    }

    @RequestMapping(value = "/nhom-cay/tat-ca")
    public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults,
                                      TreeSearchCondition searchCondition) {
        request.getSession().setAttribute("listType", 1);
        this.searchCondition = searchCondition;
        super.settingUpParams(request, model, offset, maxResults);
        return new ModelAndView("/tree/listTree");
    }


    @Override
    public void listItems(Model model) {
        if (this.searchCondition == null) {
            this.searchCondition = new TreeSearchCondition();
        }
        try {
            List<Tree> allTrees = this.treeService.getAll(this.searchCondition, null, this.getOffset(),
                    this.getMaxResults());
            int count = this.treeService.countAll(this.searchCondition);
            model.addAttribute("allTrees", allTrees);
            model.addAttribute("offset", this.getOffset());
            model.addAttribute("maxResult", this.getMaxResults());
            model.addAttribute("selectedItems", this.getSelectedItems());
            model.addAttribute("searchCondition", this.searchCondition);
            model.addAttribute("count", count);
        } catch (MyException ex) {
            model.addAttribute("message", ex.getErrorMessage());
        }
    }

    @RequestMapping(value = "/nhom-cay/them-moi", method = RequestMethod.GET)
    public ModelAndView initNewProvider(Model model) {
        Provider tree = (Provider) model.asMap().get("tree");
        if (tree == null) {
            model.addAttribute("tree", new Provider());
        } else {
            model.addAttribute("tree", tree);
        }
        return new ModelAndView("tree/addTree");
    }

    @RequestMapping(value = "/nhom-cay/them-moi", method = RequestMethod.POST)
    public RedirectView addProvider(Model model, @ModelAttribute Tree tree, RedirectAttributes redirectAttributes) {
        try {
            Long treeId = this.treeService.add(tree);
            return new RedirectView(String.valueOf(treeId));
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getErrorMessage());
            redirectAttributes.addFlashAttribute("tree", tree);
            return new RedirectView("them-moi");
        }
    }

    @RequestMapping(value = "/nhom-cay/{treeId}", method = RequestMethod.GET)
    public ModelAndView getDetails(Model model, @PathVariable Long treeId) {
        try {
            Tree tree = this.treeService.getById(treeId);
            if (tree == null) {
                throw new MyException(IMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            model.addAttribute("tree", tree);
        } catch (MyException ex) {
            return new ModelAndView("/tree/viewTree");
        }
        return new ModelAndView("/tree/viewTree");
    }

    @RequestMapping(value = "/nhom-cay/chon-don-hang", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id) {
        return super.selectItem(id);
    }

    @RequestMapping(value = "/nhom-cay/bo-chon-don-hang", method = RequestMethod.GET)
    protected @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id) {
        return super.deSelectItem(id);
    }

    @RequestMapping(value = "/nhom-cay/chon-tat-ca", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> selectAllItems(@RequestParam String ids) {
        return super.selectAllItems(ids);
    }

    @RequestMapping(value = "/nhom-cay/bo-chon-tat-ca", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectAllItems(@RequestParam String ids) {
        return this.deSelectAllItems(ids);
    }

    @RequestMapping(value = "/nhom-cay/chinh-sua/{treeId}", method = RequestMethod.GET)
    public ModelAndView editDetails(Model model, @PathVariable Long treeId) {
        try {
            Tree tree = this.treeService.getById(treeId);
            if (tree == null) {
                throw new MyException(IMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            model.addAttribute("tree", tree);
        } catch (MyException ex) {
            return new ModelAndView("/tree/editTree");
        }
        return new ModelAndView("/tree/editTree");
    }

    @RequestMapping(value = "/nhom-cay/chinh-sua/luu-thong-tin", method = RequestMethod.POST)
    public RedirectView save(Model model, @ModelAttribute Tree tree, RedirectAttributes redirectAttributes) {
        try {
            Tree treeRes = this.treeService.saveInformation(tree);
            redirectAttributes.addFlashAttribute("tree", treeRes);
        } catch (MyException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorMessage());
            redirectAttributes.addFlashAttribute("tree", tree);
        }
        return new RedirectView("../luu-thong-tin/" + tree.getId());
    }

    @RequestMapping(value = "/nhom-cay/luu-thong-tin/{treeId}", method = RequestMethod.GET)
    public String saveResult(Model model, @PathVariable Long treeId) {
		Tree tree = (Tree) model.asMap().get("tree");
        if (tree == null) {
            try {
                tree = this.treeService.getById(treeId);
                if (tree == null) {
                    return "redirect:/chinh-sua/" + treeId;
                }
            } catch (MyException e) {
                model.addAttribute("errorMessage", e.getErrorMessage());
            }
        }
        model.addAttribute("tree", tree);
        return "/tree/editTree";

    }

    @RequestMapping(value = "/nhom-cay/xoa", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> delete() {
        try {
            this.treeService.delete(this.getSelectedItems());
        } catch (Exception ex) {
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, ex.getMessage(), null), HttpStatus.SERVICE_UNAVAILABLE);
        }
        this.getSelectedItems().clear();
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }

}
