package src.controllers;

import java.io.IOException;
import src.entities.GroupeCompetence;
import src.controllers.util.JsfUtil;
import src.controllers.util.PaginationHelper;
import src.facades.GroupeCompetenceFacade;

import java.io.Serializable;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import src.entities.Assistant;

@ManagedBean(name = "groupeCompetenceController")
@SessionScoped
public class GroupeCompetenceController implements Serializable, Converter {

    private GroupeCompetence current;
    private DataModel items = null;
    @EJB
    private src.facades.GroupeCompetenceFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    private List<Assistant> listAssistantCompetent;
    private GroupeCompetence selectedGroupeCompetence;
    private int pourcentSelect;
    private List<Integer> pourcentsToSelect;

    public GroupeCompetenceController() 
    {
        this.pourcentSelect = -1;
    }

    public GroupeCompetence getSelected() {
        if (current == null) {
            current = new GroupeCompetence();
            selectedItemIndex = -1;
        }
        return current;
    }

    private GroupeCompetenceFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (GroupeCompetence) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new GroupeCompetence();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GroupeCompetenceCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (GroupeCompetence) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GroupeCompetenceUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (GroupeCompetence) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("GroupeCompetenceDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public GroupeCompetence getSelectedGroupeCompetence() {
        return selectedGroupeCompetence;
    }

    public void setSelectedGroupeCompetence(GroupeCompetence selectedGroupeCompetence) {
        this.selectedGroupeCompetence = selectedGroupeCompetence;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) 
    {
        if (value == null || !value.matches("\\d+")) {
            return null;
        }

        GroupeCompetence groupeCompetence = ejbFacade.find(Integer.valueOf(value));

        if (groupeCompetence == null) 
        {
            throw new ConverterException(new FacesMessage("Unknown operation ID: " + value));
        }

        return groupeCompetence;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) 
    {
        if (!(value instanceof GroupeCompetence) || ((GroupeCompetence) value).getId() == null) 
        {
            return null;
        }

        return String.valueOf(((GroupeCompetence) value).getId());
    }

    @FacesConverter(forClass = GroupeCompetence.class)
    public static class GroupeCompetenceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GroupeCompetenceController controller = (GroupeCompetenceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "groupeCompetenceController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof GroupeCompetence) {
                GroupeCompetence o = (GroupeCompetence) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + GroupeCompetence.class.getName());
            }
        }

    }
    
    public void groupeCompetenceChanged(AjaxBehaviorEvent event) throws IOException
    {
        int pourcent = 100;
        if(this.pourcentSelect != -1)
        {
            pourcent = pourcentSelect;
        }
        this.listAssistantCompetent = ejbFacade.getAllAssistantByGroupAndPourcent(selectedGroupeCompetence, pourcent);
        
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    public void pourcentChanged(AjaxBehaviorEvent event) throws IOException
    {
        if(this.listAssistantCompetent == null || this.listAssistantCompetent.size() <= 0)
        {
            this.listAssistantCompetent = ejbFacade.getAllAssistantByPourcent(pourcentSelect);
        }
        else
        {
            this.listAssistantCompetent = ejbFacade.getAllAssistantByGroupAndPourcent(selectedGroupeCompetence, pourcentSelect);
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    public List<Assistant> getListAssistantCompetent()
    {
        return this.listAssistantCompetent;
    }

    public int getPourcentSelect() {
        return pourcentSelect;
    }

    public void setPourcentSelect(int pourcentSelect) {
        this.pourcentSelect = pourcentSelect;
    }

    public List<Integer> getPourcentsToSelect() 
    {
        if(pourcentsToSelect == null)
        {
            this.pourcentsToSelect = new ArrayList<>(10);
            for(int i = 10; i <= 100; i += 10)
            {
                this.pourcentsToSelect.add(i);
            }
        }
        return pourcentsToSelect;
    }

    public void setPourcentsToSelect(List<Integer> pourcentsToSelect) {
        this.pourcentsToSelect = pourcentsToSelect;
    }

}
