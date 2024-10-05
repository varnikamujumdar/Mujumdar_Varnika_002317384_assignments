/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Varnika Mujumdar
 */
public class PersonDirectory {
    
    private ArrayList<Person> persons;
    
    public PersonDirectory()
    {
        this.persons=new ArrayList<Person>();
    }
    
    public ArrayList<Person> getPerson()
    {
        return persons;
    }
    
    public void setPersons(ArrayList<Person> persons)
    {
        this.persons=persons;
    }
    
    public Person addPerson()
    {
        Person p = new Person();
        persons.add(p);
        return p;
    }
    
    public void deletePerson(Person person)
    {
        persons.remove(person);
    }
    
    public Person searchPersonByFirstName(String firstName)
    {
        for(Person p : persons)
        {
            if(p.getFirstName().contains(firstName))
            {
                return p;
            }
        }
        return null;
    }
    
     public Person searchPersonByLastName(String lastName)
    {
        for(Person p : persons)
        {
            if(p.getLastName().contains(lastName))
            {
                return p;
            }
        }
        return null;
    }
     
      public Person searchPersonByHomeStreetAddress(String hstreetAddress)
    {
        for(Person p : persons)
        {
            if(p.getAddress().getHstreetAddress().contains(hstreetAddress))
            {
                return p;
            }
        }
        return null;
    }
      
      public Person searchPersonByWorkStreetAddress(String wstreetAddress)
    {
        for(Person p : persons)
        {
            if(p.getAddress().getWstreetAddress().contains(wstreetAddress))
            {
                return p;
            }
        }
        return null;
    }
     
     
    
    
    
    
    
    
    
    
    
    
    
    
}
