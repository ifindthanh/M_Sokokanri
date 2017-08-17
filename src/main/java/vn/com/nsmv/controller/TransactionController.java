package vn.com.nsmv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.entity.Role;
import vn.com.nsmv.entity.Transaction;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.service.UserService;

@Controller
@Scope("session")
public class TransactionController extends SokokanriCommonController
{

	@Autowired
	private UserService userService;

    @RequestMapping(value="/user/vi-tien/{userId}", method = RequestMethod.GET)
    public ModelAndView transactionManagement(Model model, @PathVariable Long userId) {
        try {
            User user = this.userService.getUserByCode(userId);
            if (user == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            if (!this.userService.getAllRoles(user.getId()).contains(new Role("U"))) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorAddTransactionNotAllow(LocaleContextHolder.getLocale()));
            }
            Transaction transaction = new Transaction();
            transaction.setUser(user);
            model.addAttribute("transaction", transaction);
            return new ModelAndView("/user/walletManagement");
        } catch (SokokanriException e) {
            model.addAttribute("message", e.getErrorMessage());
            return new ModelAndView("/orders/error");
        }
        
    }
    
    @RequestMapping(value="/user/vi-tien/them-giao-dich", method = RequestMethod.POST)
    public RedirectView addTransaction(Model model, @ModelAttribute Transaction transaction, RedirectAttributes redirectAttributes) {
        try {
            this.userService.saveTransaction(transaction);
            redirectAttributes.addFlashAttribute("inforMessage", SokokanriMessage.getMessageInforAddTransactionSuccessfully(LocaleContextHolder.getLocale()));
        } catch (SokokanriException e) {
            redirectAttributes.addFlashAttribute("message", e.getErrorMessage());
        }
        redirectAttributes.addFlashAttribute("transaction", transaction);
        return new RedirectView("them-giao-dich");
    }
    
    @RequestMapping(value="/user/vi-tien/them-giao-dich", method = RequestMethod.GET)
    public ModelAndView addTransactionResult(Model model) {
        Transaction transaction = (Transaction) model.asMap().get("transaction");
        if (transaction == null) {
            model.addAttribute("message", SokokanriMessage.getMessageErrorInvalidRequest(LocaleContextHolder.getLocale()));
            return new ModelAndView("/orders/error");
        }
        model.addAttribute("transaction", transaction);
        return new ModelAndView("/user/walletManagement");
    }
    
    @RequestMapping(value="/user/vi-tien/tat-ca-giao-dich/{userId}", method = RequestMethod.GET)
    public ModelAndView walletManagement(Model model, @PathVariable Long userId) {
        try {
            User user = this.userService.getUserByCode(userId);
            if (user == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorUserNotExists(LocaleContextHolder.getLocale()));
            }
            if (!this.userService.getAllRoles(user.getId()).contains(new Role("U"))) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorAddTransactionNotAllow(LocaleContextHolder.getLocale()));
            }
            List<Transaction> transactions = this.userService.listAllTransactions(userId);
            model.addAttribute("transactions", transactions);
            model.addAttribute("user", user);
            model.addAttribute("action", 1);
            return new ModelAndView("/user/transactionManagement");
        } catch (SokokanriException e) {
            model.addAttribute("message", e.getErrorMessage());
            return new ModelAndView("/orders/error");
        }
        
    }
	
}