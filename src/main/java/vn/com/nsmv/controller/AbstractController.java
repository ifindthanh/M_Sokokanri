package vn.com.nsmv.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.com.nsmv.bean.ResponseResult;
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.Utils;

public abstract class AbstractController {
    private Integer offset;
    private Integer maxResults;
    private Set<Long> selectedItems = new HashSet<Long>();
    
    protected void initController()
    {
        this.offset = 0;
        this.maxResults = Constants.MAX_IMAGE_PER_PAGE;
        this.selectedItems.clear();
    }
    
    protected void listAllOrdersInPage(HttpServletRequest request, Model model, Integer offset, Integer maxResults) {
        this.resetParams(offset, maxResults);
        this.doBusiness(model);
       
    }
    
    protected void reset(Integer offset, Integer maxResults) {
        this.selectedItems.clear();
        
        this.resetParams(offset, maxResults);
        
    }

    private void resetParams(Integer offset, Integer maxResults) {
        if (this.maxResults == null)
        {
            this.maxResults = Constants.MAX_IMAGE_PER_PAGE;
        }
        
        if (offset != null)
        {
            this.offset = offset;
        }
        
        if (maxResults != null)
        {
            this.maxResults = maxResults;
        }
    }
    
    protected abstract void doBusiness(Model model);
    
    protected @ResponseBody ResponseEntity<ResponseResult<String>> selectItem(@RequestParam Long id){
        this.selectedItems.add(id);
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }
    
    protected @ResponseBody ResponseEntity<ResponseResult<String>> deSelectItem(@RequestParam Long id){
        this.selectedItems.remove(id);
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }
    
    protected @ResponseBody ResponseEntity<ResponseResult<String>> selectAllItems(@RequestParam String ids){
        String[] allIds = ids.split(",");
        for (String item : allIds) {
            if (Utils.isEmpty(item)) {
                continue;
            }
            try {
                Long id = Long.parseLong(item);
                this.selectedItems.add(id);
            } catch (Exception ex) {
                continue;
            }
        }
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }
    
    protected @ResponseBody ResponseEntity<ResponseResult<String>> deSelectAllItems(@RequestParam String ids){
        String[] allIds = ids.split(",");
        for (String item : allIds) {
            if (Utils.isEmpty(item)) {
                continue;
            }
            try {
                Long id = Long.parseLong(item);
                this.selectedItems.remove(id);
            } catch (Exception ex) {
                continue;
            }
        }
        return new ResponseEntity<ResponseResult<String>>(new ResponseResult<String>(1, "Success", null), HttpStatus.OK);
    }

    
    public Integer getOffset() {
        return offset;
    }

    
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    
    public Integer getMaxResults() {
        return maxResults;
    }

    
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    
    
    protected Set<Long> getSelectedItems() {
        return selectedItems;
    }

    
    protected void setSelectedItems(Set<Long> selectedItems) {
        this.selectedItems = selectedItems;
    }
    
    protected void checkSearching(List<String> searchedItems, List<String> allItems){
        
        for (String item:searchedItems) {
            if (allItems.contains(item)) {
                continue;
            }
            allItems.add(item);
        }
    }
    
}
