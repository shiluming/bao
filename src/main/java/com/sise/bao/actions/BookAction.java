package com.sise.bao.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.sise.bao.VO.BookSearchVO;
import com.sise.bao.VO.UserVO;
import com.sise.bao.entity.Book;
import com.sise.bao.entity.BookCategory;
import com.sise.bao.entity.User;
import com.sise.bao.service.BookCategoryManager;
import com.sise.bao.service.BookServiceManager;
import com.sise.bao.service.UserServiceManager;
import com.sise.bao.utils.Page;
import com.sise.bao.utils.Struts2Utils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.struts2.convention.annotation.AllowedMethods;
import org.apache.struts2.convention.annotation.Namespace;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by rola on 2017/4/25.
 */
@Namespace("/")
@AllowedMethods(value={"list","addCategory","register","input","login","edit","fileUpload","addBookCategory","search","delete"})
public class BookAction extends ActionSupport{

    private File file;

    private String filename;

    private String fileFileName;

    private Book book;

    private Page<Book> page;
    
    private UserVO userVO;

    private BookSearchVO searchVO;
    
    private User user;

    private BookCategory bookCategory;
    
    private UserServiceManager userServiceManager = new UserServiceManager();

    private BookCategoryManager bookCategoryManager = new BookCategoryManager();

    private BookServiceManager bookServiceManager = new BookServiceManager();

    public String list() {
        if(searchVO == null) {
            searchVO = new BookSearchVO();
        }
        if(page == null) {
            page = new Page<Book>();
        }
        page = bookServiceManager.search(page,searchVO);

        return "main";
    }

    public String search() {
        if(searchVO == null) {
            searchVO = new BookSearchVO();
        }
        if(page == null) {
            page = new Page<Book>();
        }
        page.setPageSize(8);
        page = bookServiceManager.search(page,searchVO);
        Map<String, Object> result = new HashMap<String,Object>();
        Map<String, Object> rows = new HashMap<String,Object>();
        rows.put("row",page.getResult().size()%4==0?page.getResult().size()/4:page.getResult().size()/4+1);
        rows.put("data",page.getResult());
        result.put("success",true);
        result.put("rows",rows);
        result.put("totals",page.getTotalCount());
        Struts2Utils.renderJson(result);
        return null;
    }

    public String input() {

        Map<String, Object> result = new HashMap<String,Object>();

        if(book == null) {
            return "input";
        }
        if(book.getId() == null) {
            bookServiceManager.save(book);
            result.put("success",true);
            result.put("msg","‰øùÂ≠òÊàêÂäü");
            Struts2Utils.renderJson(result);
            return null;
        }else {
            bookServiceManager.update(book);
            result.put("success",true);
            result.put("msg","‰øÆÊîπÊàêÂäü");
            Struts2Utils.renderJson(result);
            return null;
        }
    }

    /**
     * Ê∑ªÂä†Âõæ‰π¶Á±ªÂà´
     * @return
     */
    public String addBookCategory() {
        Map<String, Object> result = new HashMap<String,Object>();
        if(bookCategory == null) {
            return "category";
        }
        if(bookCategory.getId() != null) {
            bookCategoryManager.update(bookCategory);
            result.put("success",true);
            result.put("msg","‰øÆÊîπÂõæ‰π¶Á±ªÂà´ÊàêÂäü");
            Struts2Utils.renderJson(result);
            return null;
        }else {
            bookCategoryManager.save(bookCategory);
            result.put("success",true);
            result.put("msg","Êñ∞Â¢ûÂõæ‰π¶Á±ªÂà´ÊàêÂäü");
            Struts2Utils.renderJson(result);
            return null;
        }
    }
    
    public String delete() {
    	Map<String, Object> result = new HashMap<String, Object>();
    	if( book== null) {
    		result.put("success", false);
    		result.put("msg", "error");
    		Struts2Utils.renderJson(result);
    		return null;
    	}
    	book = bookServiceManager.getById(book.getId());
    	bookServiceManager.delete(book);
    	result.put("success", true);
		result.put("msg", "success");
		Struts2Utils.renderJson(result);
    	return null;
    }

    /**
     * Êñá‰ª∂‰∏ä‰º†
     * @return
     */
    public String fileUpload() {
        Map<String,Object> result  = new HashMap<String,Object>();
        System.out.println("fileupload" + file );
        try {
            String filePath = Struts2Utils.getRequest().getSession().getServletContext().getRealPath("/");
            System.out.println(filePath);
            FileUtils.copyFile(file,new File(filePath+System.getProperty("file.separator")+
                    "images"+System.getProperty("file.separator")+fileFileName));

        } catch (IOException e) {
            e.printStackTrace();

        }
        System.out.println("filename = " + fileFileName);
        result.put("success",true);
        result.put("data","dsfdsf");
        Struts2Utils.renderJson(result);
        return null;
    }
    
    /**
     * µ«¬º∑Ω∑®
     * @return
     */
    public String login() {
    	Map<String, Object> result = new HashMap<String, Object>();
    	Page<User> userPage = new Page<User>();
    	userPage = userServiceManager.login(userVO, userPage);
    	if(userPage.getResult().size()>0) {
    		result.put("success", true);
    		result.put("msg", "µ«¬º≥…π¶");
    		Struts2Utils.getRequest().getSession().setAttribute("loginUser", userPage.getResult().get(0));
    		Struts2Utils.renderJson(result);
    		return null;
    	}else {
    		result.put("success", false);
    		result.put("msg", "µ«¬º ß∞‹,’À∫≈√‹¬Î¥ÌŒÛ£°");
    		Struts2Utils.renderJson(result);
    		return null;
    	}
    }
    
    /**
     * ◊¢≤·
     * @return
     */
    public String register() {
    	Map<String, Object> result = new HashMap<String, Object>();
    	if(user != null) {
    		result.put("success", true);
    		result.put("msg", "◊¢≤·≥…π¶");
    		userServiceManager.register(user);
    		Struts2Utils.renderJson(result);
    		return null;
    	}
    	result.put("success", false);
		result.put("msg", "◊¢≤· ß∞‹");
    	return null;
    }
    
    public String addCategory() {
    	Map<String, Object> result = new HashMap<String, Object>();
    	if(bookCategory!=null) {
    		result.put("success", true);
    		result.put("msg", "±£¥Ê≥…π¶");
    		bookCategoryManager.save(bookCategory);
    		Struts2Utils.renderJson(result);
    		return null;
    	} else {
    		
    		return "category";
    	}
    }

    /***************** GET/SET ********************/

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public BookSearchVO getSearchVO() {
        return searchVO;
    }

    public void setSearchVO(BookSearchVO searchVO) {
        this.searchVO = searchVO;
    }

    public Page<Book> getPage() {
        return page;
    }

    public void setPage(Page<Book> page) {
        this.page = page;
    }

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
    
}
