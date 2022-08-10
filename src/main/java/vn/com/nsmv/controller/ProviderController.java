package vn.com.nsmv.controller;

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
import vn.com.nsmv.bean.ResponseResult;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Provider;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.ProviderSearchCondition;
import vn.com.nsmv.service.ProviderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Scope("session")
public class ProviderController extends AbstractController {

    private ProviderSearchCondition searchCondition;

    @Autowired
    private ProviderService providerService;

    @RequestMapping(value = "/provider/tat-ca/0", method = RequestMethod.GET)
    public String init() {
        super.initController();
        searchCondition = new ProviderSearchCondition();
        return "redirect:/provider/tat-ca";
    }

    @RequestMapping(value = "/provider/tat-ca")
    public ModelAndView listAllOrders(HttpServletRequest request, Model model, Integer offset, Integer maxResults
            , ProviderSearchCondition searchCondition) {
        request.getSession().setAttribute("listType", 1);
		if (searchCondition != null) {
			this.searchCondition = searchCondition;
		}
        super.settingUpParams(request, model, offset, maxResults);

        return new ModelAndView("/provider/listProviders");
    }


    @Override
    public void listItems(Model model) {
        if (this.searchCondition == null) {
            this.searchCondition = new ProviderSearchCondition();
        }
        try {
            List<Provider> allProviders = this.providerService.getAll(this.searchCondition, null, this.getOffset(),
                    this.getMaxResults());
            int count = this.providerService.countAll(this.searchCondition);
            model.addAttribute("allProvides", allProviders);
            model.addAttribute("offset", this.getOffset());
            model.addAttribute("maxResult", this.getMaxResults());
            model.addAttribute("selectedItems", this.getSelectedItems());
            model.addAttribute("searchCondition", this.searchCondition);
            model.addAttribute("count", count);
        } catch (SokokanriException ex) {
            model.addAttribute("message", ex.getErrorMessage());
        }
    }

    @RequestMapping(value = "/provider/them-moi", method = RequestMethod.GET)
    public ModelAndView initNewProvider(Model model) {
        Provider provider = (Provider) model.asMap().get("provider");
        if (provider == null) {
            model.addAttribute("provider", new Provider());
        } else {
            model.addAttribute("provider", provider);
        }
        return new ModelAndView("provider/addProvider");
    }

    @RequestMapping(value = "/provider/them-moi", method = RequestMethod.POST)
    public RedirectView addProvider(Model model, @ModelAttribute Provider provider, RedirectAttributes redirectAttributes) {
        try {
            Long providerId = this.providerService.add(provider);
            return new RedirectView(String.valueOf(providerId));
        } catch (SokokanriException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getErrorMessage());
            redirectAttributes.addFlashAttribute("provider", provider);
            return new RedirectView("them-moi");
        }
    }

    @RequestMapping(value = "/provider/{providerId}", method = RequestMethod.GET)
    public ModelAndView getDetails(Model model, @PathVariable Long providerId) {
        try {
            Provider provider = this.providerService.getById(providerId);
            if (provider == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            model.addAttribute("provider", provider);
        } catch (SokokanriException ex) {
            return new ModelAndView("/provider/viewProvider");
        }
        return new ModelAndView("/provider/viewProvider");
    }

    @RequestMapping(value = "/provider/chon-don-hang", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id) {
        return super.selectItem(id);
    }

    @RequestMapping(value = "/provider/bo-chon-don-hang", method = RequestMethod.GET)
    protected @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id) {
        return super.deSelectItem(id);
    }

    @RequestMapping(value = "/provider/chon-tat-ca", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> selectAllItems(@RequestParam String ids) {
        return super.selectAllItems(ids);
    }

    @RequestMapping(value = "/provider/bo-chon-tat-ca", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> deSelectAllItems(@RequestParam String ids) {
        return this.deSelectAllItems(ids);
    }

    @RequestMapping(value = "/provider/chinh-sua/{providerId}", method = RequestMethod.GET)
    public ModelAndView editDetails(Model model, @PathVariable Long providerId) {
        try {
            Provider provider = this.providerService.getById(providerId);
            if (provider == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            model.addAttribute("provider", provider);
        } catch (SokokanriException ex) {
            return new ModelAndView("/provider/editProvider");
        }
        return new ModelAndView("/provider/editProvider");
    }

    @RequestMapping(value = "/provider/chinh-sua/luu-thong-tin", method = RequestMethod.POST)
    public RedirectView save(Model model, @ModelAttribute Provider provider, RedirectAttributes redirectAttributes) {
        try {
            Provider providerRes = this.providerService.saveInformation(provider);
            redirectAttributes.addFlashAttribute("provider", providerRes);
        } catch (SokokanriException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getErrorMessage());
            redirectAttributes.addFlashAttribute("provider", provider);
        }
        return new RedirectView("../luu-thong-tin/" + provider.getId());
    }

    @RequestMapping(value = "/provider/luu-thong-tin/{providerId}", method = RequestMethod.GET)
    public String saveResult(Model model, @PathVariable Long providerId) {
        Provider provider = (Provider) model.asMap().get("provider");
        if (provider == null) {
            try {
                provider = this.providerService.getById(providerId);
                if (provider == null) {
                    return "redirect:/chinh-sua/" + providerId;
                }
            } catch (SokokanriException e) {
                model.addAttribute("errorMessage", e.getErrorMessage());
            }
        }
        model.addAttribute("provider", provider);
        return "/provider/editProvider";

    }

    @RequestMapping(value = "/provider/xoa-provider", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<ResponseResult<String>> delete() {
        try {
            this.providerService.delete(this.getSelectedItems());
        } catch (Exception ex) {
            return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(0, ex.getMessage(), null), HttpStatus.SERVICE_UNAVAILABLE);
        }
        this.getSelectedItems().clear();
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }

}
