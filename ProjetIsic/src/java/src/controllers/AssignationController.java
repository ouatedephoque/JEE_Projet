package src.controllers;

import src.entities.Assignation;
import src.controllers.util.JsfUtil;
import src.controllers.util.PaginationHelper;
import src.facades.AssignationFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import src.entities.Assistant;
import src.entities.GroupeCompetence;
import src.entities.Projet;

@ManagedBean(name = "assignationController")
@SessionScoped
public class AssignationController implements Serializable, Converter {

    private Assignation current;
    private DataModel items = null;
    @EJB
    private src.facades.AssignationFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public AssignationController() {
    }

    public Assignation getSelected() {
        if (current == null) {
            current = new Assignation();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AssignationFacade getFacade() {
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
        items = getItems();
        return "/user/assignation/List";
    }

    public String prepareView() {
        current = (Assignation) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/user/assignation/View";
    }

    public String prepareCreate() {
        current = new Assignation();
        selectedItemIndex = -1;
        return "/admin/assignation/Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AssignationCreated"));
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Assignation) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "/admin/assignation/Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AssignationUpdated"));
            return "/user/assignation/View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Assignation) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "/user/assignation/List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "/user/assignation/View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "/user/assignation/List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AssignationDeleted"));
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

    public DataModel getItems() 
    {
        if(items == null)
        {
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

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) 
    {
        if (value == null || !value.matches("\\d+")) {
            return null;
        }

        Assignation assignation = ejbFacade.find(Integer.valueOf(value));

        if (assignation == null) 
        {
            throw new ConverterException(new FacesMessage("Unknown operation ID: " + value));
        }

        return assignation;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) 
    {
        if (!(value instanceof Assignation) || ((Assignation) value).getAssignationId()== null) 
        {
            return null;
        }

        return String.valueOf(((Assignation) value).getAssignationId());
    }

    @FacesConverter(forClass = Assignation.class)
    public static class AssignationControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AssignationController controller = (AssignationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "assignationController");
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
            if (object instanceof Assignation) {
                Assignation o = (Assignation) object;
                return getStringKey(o.getAssignationId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Assignation.class.getName());
            }
        }
    }
    
    public String getMyAssignationEdit(Assignation assignation)
    {
        if(current == null)
        {
            current = assignation;
        }
        return "/admin/assignation/Edit";
    }

}
