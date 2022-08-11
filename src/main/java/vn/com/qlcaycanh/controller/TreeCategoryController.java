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
import vn.com.qlcaycanh.entity.Tree;
import vn.com.qlcaycanh.entity.TreeCategory;
import vn.com.qlcaycanh.i18n.IMessage;
import vn.com.qlcaycanh.javabean.TreeCategorySearchCondition;
import vn.com.qlcaycanh.service.TreeCategoryService;
import vn.com.qlcaycanh.service.TreeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Scope("session")
public class TreeCategoryController extends AbstractController {

    private TreeCategorySearchCondition searchCondition;

    @Autowired
    private TreeCategoryService treeCatService;

    @Autowired
    private TreeService treeService;

    @RequestMapping(value = "/cay/tat-ca/0", method = RequestMethod.GET)
    public String init() {
        super.initController();
        searchCondition = new TreeCategorySearchCondition();
        return "redirect:/cay/tat-ca";
    }

    @RequestMapping(value = "/cay/tat-ca")
    public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults
            , TreeCategorySearchCondition searchCondition) throws MyException {
        request.getSession().setAttribute("listType", 1);
        this.searchCondition = searchCondition;
        List<Tree> allTreeGroups = treeService.getAll(null, null, null, null);
        model.addAttribute("allTreeGroups", allTreeGroups);
        super.settingUpParams(request, model, offset, maxResults);
        return new ModelAndView("/treeCart/listTreeCart");
    }


    @Override
    public void listItems(Model model) {
        if (this.searchCondition == null) {
            this.searchCondition = new TreeCategorySearchCondition();
        }
        try {
            List<TreeCategory> allTrees = this.treeCatService.getAll(this.searchCondition, null, this.getOffset(),
                    this.getMaxResults());
            int count = this.treeCatService.countAll(this.searchCondition);
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

    @RequestMapping(value = "/cay/them-moi", method = RequestMethod.GET)
    public ModelAndView initNewProvider(Model model) throws MyException {
        TreeCategory tree = (TreeCategory) model.asMap().get("tree");
        if (tree == null) {
            model.addAttribute("tree", new TreeCategory());
        } else {
            model.addAttribute("tree", tree);
        }
        List<Tree> allTreeGroups = treeService.getAll(null, null, null, null);
        model.addAttribute("allTreeGroups", allTreeGroups);
        return new ModelAndView("treeCart/addTreeCart");
    }

    @RequestMapping(value = "/cay/them-moi", method = RequestMethod.POST)
    public RedirectView addProvider(Model model, @ModelAttribute TreeCategory tree, RedirectAttributes redirectAttributes) {
        try {
            Long treeId = this.treeCatService.add(tree);
            return new RedirectView(String.valueOf(treeId));
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getErrorMessage());
            redirectAttributes.addFlashAttribute("tree", tree);
            return new RedirectView("them-moi");
        }
    }

    @RequestMapping(value = "/cay/{treeId}", method = RequestMethod.GET)
    public ModelAndView getDetails(Model model, @PathVariable Long treeId) {
        try {
            TreeCategory tree = this.treeCatService.getById(treeId);
            if (tree == null) {
                throw new MyException(IMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            model.addAttribute("tree", tree);
        } catch (MyException ex) {
            return new ModelAndView("/treeCart/viewTreeCart");
        }
        return new ModelAndView("/treeCart/viewTreeCart");
    }

    @RequestMapping(value = "/cay/chon-don-hang", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id) {
        return super.selectItem(id);
    }

    @RequestMapping(value = "/cay/bo-chon-don-hang", method = RequestMethod.GET)
    protected @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id) {
        return super.deSelectItem(id);
    }

    @RequestMapping(value = "/cay/chon-tat-ca", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> selectAllItems(@RequestParam String ids) {
        return super.selectAllItems(ids);
    }

    @RequestMapping(value = "/cay/bo-chon-tat-ca", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectAllItems(@RequestParam String ids) {
        return this.deSelectAllItems(ids);
    }

    @RequestMapping(value = "/cay/chinh-sua/{treeId}", method = RequestMethod.GET)
    public ModelAndView editDetails(Model model, @PathVariable Long treeId) {
        try {
            TreeCategory tree = this.treeCatService.getById(treeId);
            if (tree == null) {
                throw new MyException(IMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            List<Tree> allTreeGroups = treeService.getAll(null, null, null, null);
            model.addAttribute("allTreeGroups", allTreeGroups);
            model.addAttribute("tree", tree);
        } catch (MyException ex) {
            return new ModelAndView("/treeCart/editTreeCart");
        }
        return new ModelAndView("/treeCart/editTreeCart");
    }

    @RequestMapping(value = "/cay/chinh-sua/luu-thong-tin", method = RequestMethod.POST)
    public RedirectView save(Model model, @ModelAttribute TreeCategory tree, RedirectAttributes redirectAttributes) {
        try {
            TreeCategory treeRes = this.treeCatService.saveInformation(tree);
            redirectAttributes.addFlashAttribute("tree", treeRes);
        } catch (MyException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorMessage());
            redirectAttributes.addFlashAttribute("tree", tree);
        }
        return new RedirectView("../luu-thong-tin/" + tree.getId());
    }

    @RequestMapping(value = "/cay/luu-thong-tin/{treeId}", method = RequestMethod.GET)
    public String saveResult(Model model, @PathVariable Long treeId) throws MyException {
        List<Tree> allTreeGroups = treeService.getAll(null, null, null, null);
        model.addAttribute("allTreeGroups", allTreeGroups);
        TreeCategory tree = (TreeCategory) model.asMap().get("tree");
        if (tree == null) {
            try {
                tree = this.treeCatService.getById(treeId);
                if (tree == null) {
                    return "redirect:/chinh-sua/" + treeId;
                }
            } catch (MyException e) {
                model.addAttribute("errorMessage", e.getErrorMessage());
            }
        }
        model.addAttribute("tree", tree);
        return "/treeCart/editTreeCart";

    }

    @RequestMapping(value = "/cay/xoa", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> delete() {
        try {
            this.treeCatService.delete(this.getSelectedItems());
        } catch (Exception ex) {
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, ex.getMessage(), null), HttpStatus.SERVICE_UNAVAILABLE);
        }
        this.getSelectedItems().clear();
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }

}
