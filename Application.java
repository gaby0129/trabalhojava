package com.mycompany.projetosimone;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Application {
    public final Scanner ler = new Scanner(System.in);
    public final Connection conn;
    public final Statement st;
    
    public Application (Connection conn, Statement st) {
        this.conn = conn;
        this.st = st;
    }
    
    public void clear () {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException e) {
            System.err.format("Clear error: %s", e.getMessage());
        }
    }
    
    public void FrontEnd () {
        int escolha;
        clear();
        System.out.println("---------- SEJA BEM-VINDO AO JAVA DATABASE ----------");
        while (true) {
            System.out.println("Primeiro você precisa criar ou acessar o banco de dados.");
            System.out.println("    1 - Criar tabela.");
            System.out.println("    2 - Inserir dados.");
            System.out.println("    3 - Ler dados.");
            System.out.println("    4 - Atualizar dados.");
            System.out.println("    5 - Excluir dados.");
            System.out.println("    0 - Sair.");
            System.out.print("Escolha: ");
            escolha = ler.nextInt();
            clear();
            switch (escolha) {
                case 1:
                    this.createTable();
                    break;
                case 2:
                    this.insertData();
                    break;
                case 3:
                    this.readData();
                    break;
                case 4:
                    this.updateData();
                    break;
                case 5:
                    this.deleteData();
                    break;
                case 0:
                    System.out.println("Adeus!");
                    return;
                default:
                    System.out.println("Erro.");
                    break;
            }
        }
    }
    
    public void createTable () {
        try {
            System.out.print("Nome da tabela: ");
            String nomeTabela = ler.next();
            String SQLCriarTabela = "CREATE TABLE " + nomeTabela + " (cpf int, nome VARCHAR(60));";
            System.out.println(SQLCriarTabela);
            st.executeUpdate(SQLCriarTabela);
            System.out.println("Database created...");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    
    public void insertData () {
        try {
            System.out.print("CPF: ");
            int CPF = ler.nextInt();
            System.out.print("Nome: ");
            String nome = ler.next();
            String SQLInserirDados = "INSERT INTO pessoa (cpf, nome) VALUES (" + CPF + ", \'" + nome + "\');";
            System.out.println(SQLInserirDados);
            st.executeUpdate(SQLInserirDados);
            System.out.println("Data inserted...");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    
    public void readData () {
        ResultSet result;
        try {
            String SQLLerDados = "SELECT * FROM pessoa";
            result = st.executeQuery(SQLLerDados);
            while (result.next()) {
                System.out.println("--------------------------------------------------");
                System.out.println("CPF: " + result.getString(1));
                System.out.println("Nome: " + result.getString(2));
            }
            result.close();
            System.out.println("Data read...");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    
    public void updateData () {
        try {
            System.out.print("Nome: ");
            String nome = ler.next();
            String SQLAtualizarDados = "UPDATE pessoa SET nome = \'" + nome + "\';";
            System.out.println(SQLAtualizarDados);
            st.executeUpdate(SQLAtualizarDados);
            System.out.println("Data updated...");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
    
    public void deleteData () {
        try {
            String SQLInserirDados = "DELETE FROM pessoa;";
            System.out.println(SQLInserirDados);
            st.executeUpdate(SQLInserirDados);
            System.out.println("Data deleted...");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
}