package br.com.entidades;

import java.util.Scanner;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ManyToOne {

	public static void main( String[ ] args ) throws IOException 
	   {		
		  
		//LOGGER
		Log meuLogger = new Log("Log.txt");
		try {
			//Log meuLogger = new Log("Log.txt");
			meuLogger.logger.setLevel(Level.FINE);
			meuLogger.logger.info("Log de informação");
			meuLogger.logger.warning("Log de Aviso");
			meuLogger.logger.severe("Log Severo");
			

		} catch (Exception e) {
			meuLogger.logger.info("Exception:" + e.getMessage()); //escrever no arquivo de log o erro
            e.printStackTrace();//escrever no console o erro

		}
	   
				
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "ManyToOne" ); //criar o gerenciador da fábrica de entidades
        EntityManager entitymanager = emfactory.createEntityManager( ); //criar uma entidade
        meuLogger.logger.info("\nA entidade manager factory ManyToOne foi criada!!");//escrever no log
	      
        entitymanager.getTransaction( ).begin( ); //abrir um conexão para o bd com a entidade criada

        //Criando a entidade departamento
        Department department = new Department();
        //department.setId(1); //setar o nome do departamento desenvolvedor
        department.setName("Development"); //setar o nome do departamento desenvolvedor
        //Store Department
        entitymanager.persist(department); //salvar o departamento do bd
        
     // Solicitando os dados do funcionário
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Quantos funcionários deseja incluir?");
        int qtd = scanner.nextInt();
        

        try {
            for (int i = 0; i < qtd; i++) {
                scanner.nextLine();

                System.out.println("Digite o nome do funcionário:");
                String ename = scanner.nextLine();

                System.out.println("Digite o salário do funcionário:");
                double salary = scanner.nextDouble();
                scanner.nextLine();

                System.out.println("Digite o cargo do funcionário:");
                String deg = scanner.nextLine();

                Employee employee = new Employee();
                employee.setEname(ename);
                employee.setSalary(salary);
                employee.setDeg(deg);
                employee.setDepartment(department);
                entitymanager.persist(employee);
            }

            entitymanager.getTransaction().commit();
        } catch (Exception e) {
            meuLogger.logger.info("Exception:" + e.getMessage());
            e.printStackTrace();
            entitymanager.getTransaction().rollback();
        } finally {
            entitymanager.close();
            emfactory.close();
            scanner.close();
        }
    }
}