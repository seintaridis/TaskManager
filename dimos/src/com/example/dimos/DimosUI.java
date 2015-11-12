package com.example.dimos;
 

import com.example.dimos.DimosUI;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.themes.ValoTheme;

import java.util.*;

import javax.servlet.annotation.WebServlet;




 
public class DimosUI extends UI {
 
    private IBackend backend;
 
    private FieldGroup fieldGroup = new FieldGroup();
 
    private Table table;
    private GridLayout form;
    private HorizontalLayout tableControls;
    private HorizontalLayout formControls;
    private HorizontalLayout create;
 
    
    
    
   	@WebServlet(value = "/*", asyncSupported = true)
   	@VaadinServletConfiguration(productionMode = false, ui = DimosUI.class)
   	public static class Servlet extends VaadinServlet {
   	}
    
    
    @Override
    protected void init(VaadinRequest request) {
 
        backend = new Backend();
 
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
 
        mainLayout.addComponent(new Label("Projects"));
        mainLayout.addComponent(buildTableControls());
        mainLayout.addComponent(buildTable());
        
        //mainLayout.addComponent(buildFormControls());
 
        setContent(mainLayout);
    }
 
    private Component buildTable() {
        table = new Table(null);
        table.setSelectable(true);
        table.setImmediate(true);
        
 
        table.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                editPerson((Person) table.getValue());
            }
        });
 
        create_table();
 
        return table;
    }
    
   
 
    private Component buildForm() {
        form = new GridLayout(2, 3);
 
        TextField name = new TextField("Name:");
        TextField customer = new TextField("Customer:");
        TextField projecttype = new TextField("Project Type:");
        DateField startdate = new DateField("Start Date:");
        DateField enddate = new DateField("End Date:");
        DateField nextdeadline = new DateField("Next Deadline:");
        TextField active = new TextField("Active:");
        TextField budget = new TextField("Budget (mandays):");
        TextArea description=new TextArea("Description:");
        TextField insertedby=new TextField("Inserted by");
        DateField insertedat = new DateField("Inserted at:");
        TextField modifiedby=new TextField("Modified by");
        DateField modifiedat = new DateField("Modified at:");
        
        
        
        fieldGroup.bind(name, "firstname");
        fieldGroup.bind(customer, "customer");
        fieldGroup.bind(projecttype, "projecttype");
        fieldGroup.bind(startdate, "startdate");
        fieldGroup.bind(enddate, "enddate");
        fieldGroup.bind(nextdeadline, "nextdeadline");
        fieldGroup.bind(active, "active");
        fieldGroup.bind(budget, "budget");
        fieldGroup.bind(description, "description");
        fieldGroup.bind(insertedby, "insertedby");
        fieldGroup.bind(insertedat, "insertedat");
        fieldGroup.bind(modifiedby, "modifiedby");
        fieldGroup.bind(modifiedat, "modifiedat");
 
        form.addComponent(name);
        form.addComponent(customer);
        form.addComponent(projecttype);
        form.addComponent(startdate);
        form.addComponent(enddate);
        form.addComponent(nextdeadline);
        form.addComponent(active);
        form.addComponent(budget);
        form.addComponent(description);
        form.addComponent(insertedby);
        form.addComponent(insertedat);
        form.addComponent(modifiedby);
        form.addComponent(modifiedat);
        return form;
    }
 
    private Component buildTableControls() {
        tableControls = new HorizontalLayout();
        Button add = new Button("New Project", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
            	
            	Window subWindow = new Window("View/Edit/Create Project");
                VerticalLayout subContent = new VerticalLayout();
                subContent.setMargin(true);
                subWindow.setContent(subContent);
                
                // Put some components in it
                subContent.addComponent(buildForm());
                subContent.addComponent(buildFormControls());
                // Center it in the browser window
                subWindow.center();
                
                // Open it in the UI
                addWindow(subWindow);
                
                
              editPerson(new Person());
                updateTableData();
            }
        });
        Button delete = new Button("Delete", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                backend.deletePerson((Person) table.getValue());
                updateTableData();
            }
        });
        tableControls.addComponent(add);
        tableControls.addComponent(delete);
        return tableControls;
    }
 
    private Component buildFormControls() {
        formControls = new HorizontalLayout();
        Button save = new Button("Ok", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                try {
                    fieldGroup.commit();
                    backend.storePerson(((BeanItem<Person>) fieldGroup
                            .getItemDataSource()).getBean());
                    updateTableData();
                    editPerson(null);
                } catch (FieldGroup.CommitException e) {
                    e.printStackTrace();
                }
            }
        });
        Button discard = new Button("Cancel", new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                fieldGroup.discard();
            }
        });
        formControls.addComponent(save);
        formControls.addComponent(discard);
        return formControls;
    }
 
    private void editPerson(Person person) {
        if (person == null) {
            person = new Person();
        }
        BeanItem<Person> item = new BeanItem<Person>(person);
        fieldGroup.setItemDataSource(item);
    }
 
   
    
    
    
    
    
    
    
    
    
    
    
    private void create_table(){
    	 List<Person> persons = backend.getPersons();
         BeanItemContainer<Person> container = new BeanItemContainer<Person>(
                 Person.class, persons);
         table.setContainerDataSource(container);
  
         table.setVisibleColumns(new String[]{
                 "firstname", "customer", "projecttype", "startdate", "enddate","nextdeadline","active","budget"});
         table.setColumnHeaders(new String[]{
                 "Name", "Customer", "Project Type", "Start Date", "End Date","Next Deadline","Active","Budget (mandays)"});

        
         table.addGeneratedColumn("view", new ColumnGenerator() {
         	
         	 
        	  @Override public Object generateCell(final Table source, final Object itemId, Object columnId) {
        	 
        		  Button delete = new Button("View", new Button.ClickListener() {
        	            public void buttonClick(Button.ClickEvent event) {
        	                backend.deletePerson((Person) table.getValue());
        	                updateTableData();
        	            }
        	        });
        	 
        	 
        	    return delete;
        	  }
        	});
         
         
         table.addGeneratedColumn("edit", new ColumnGenerator() {
         	
         	 
        	  @Override public Object generateCell(final Table source, final Object itemId, Object columnId) {
        	 
        		  Button edit = new Button("Edit", new Button.ClickListener() {
        	            public void buttonClick(Button.ClickEvent event) {
        	            	Window subWindow = new Window("View/Edit/Create Project");
        	                VerticalLayout subContent = new VerticalLayout();
        	                subContent.setMargin(true);
        	                subWindow.setContent(subContent);
        	                
        	                // Put some components in it
        	                subContent.addComponent(form);
        	                subContent.addComponent(buildFormControls());
        	                // Center it in the browser window
        	                subWindow.center();
        	                
        	                // Open it in the UI
        	                addWindow(subWindow);
        	                
        	                
        	              editPerson(new Person());
        	                updateTableData();
        	            }
        	        });
        	 
        	 
        	    return edit;
        	  }
        	});
         
         
         table.addGeneratedColumn("delete", new ColumnGenerator() {
        	
         	 
         	  @Override public Object generateCell(final Table source, final Object itemId, Object columnId) {
         	 
         		  Button delete = new Button("Delete", new Button.ClickListener() {
         	            public void buttonClick(Button.ClickEvent event) {
         	                backend.deletePerson((Person) itemId);
         	                updateTableData();
         	            }
         	        });
         	 
         	 
         	    return delete;
         	  }
         	});
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void updateTableData() {
        List<Person> persons = backend.getPersons();
        BeanItemContainer<Person> container = new BeanItemContainer<Person>(
                Person.class, persons);
        table.setContainerDataSource(container);
        table.setVisibleColumns(new String[]{
                "firstname", "customer", "projecttype", "startdate", "enddate","nextdeadline","active","budget","view","edit","delete"});
        table.setColumnHeaders(new String[]{
                "Name", "Customer", "Project Type", "Start Date", "End Date","Next Deadline","Active","Budget (mandays)","View","Edit","Delete"});
 
       
        
    
        
    }
 
    public interface IBackend {
        List<Person> getPersons();
        boolean storePerson(Person person);
        boolean deletePerson(Person person);
    }
 
    public class Backend implements IBackend {
 
        private Set<Person> people = new HashSet<Person>();
 
        public List<Person> getPersons() {
           if( people.size() == 0 ) {
               int numPeople = 100;
               for( int i = 0; i<numPeople; i++ ){
                    int id = i;

                    Person person = new Person();
                    person.setId( id );
                    person.setFirstname( "User" );
                    person.setCustomer( "Ln#" + (id + 1) );
                    Button pictureButton = new Button();
                   // pictureButton.setStyleName(ValoTheme.BUTTON_LINK);

                   // pictureButton.setIcon(new ThemeResource("../../icons/deleteIcon.png"));
                    //pictureButton.setStyleName(Button.DESIGN_ATTR_PLAIN_TEXT);
                    //pictureButton.setIcon(new ExternalResource("http://wdl.tmimgcdn.com/img_articles/20753/deleteIcon.png"));
                    
                  //  person.setButton(pictureButton);
 
                    people.add(person);
               }
           }
            return new ArrayList<Person>(people);
        }
 
        public boolean storePerson(Person person) {
            System.out.println("storePerson");
            return people.add(person);
        }
 
        public boolean deletePerson(Person person) {
            return people.remove(person);
        }
    }
}