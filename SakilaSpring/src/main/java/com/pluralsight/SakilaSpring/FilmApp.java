package com.pluralsight.SakilaSpring;

import com.pluralsight.SakilaSpring.dao.IFilmDao;
import com.pluralsight.SakilaSpring.models.Film;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class FilmApp implements CommandLineRunner {

    private final IFilmDao filmDao;

    public FilmApp(@Qualifier("jdbcFilmDao") IFilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                printMenu();
                int choice = readInt(scanner);

                switch (choice) {
                    case 1 -> displayAllFilms();
                    case 2 -> addFilm(scanner);
                    case 3 -> {
                        System.out.println("Peace Out Homie! ✌️");
                        running = false;
                    }
                    default -> System.out.println("Invalid selection. Please try again.");
                }
            }
        }
    }

    private void printMenu() {
        System.out.print("""
                === Welcome to the Film Admin Menu ===
                    1) List All Films
                    2) Add a Film
                    3) Exit
                Enter your choice: 
                """);
    }

    private int readInt(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException ex) {
            scanner.next(); // discard invalid input
            return -1;
        }
    }

    private void displayAllFilms() {
        List<Film> films = filmDao.getAll();

        if (films.isEmpty()) {
            System.out.println("No films found.");
            return;
        }

        films.forEach(System.out::println);
    }

    private void addFilm(Scanner scanner) {
        scanner.nextLine(); // consume newline

        System.out.println("Enter film title: ");
        String title = scanner.nextLine().trim();

        System.out.println("Enter rental rate: ");
        double rentalRate = readRentalRate(scanner);

        Film newFilm = new Film(null, title, rentalRate);
        filmDao.add(newFilm);

        System.out.println("Film added successfully!");
    }

    private double readRentalRate(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException ex) {
                scanner.next(); // discard invalid input
                System.out.println("Please enter a valid number for rental rate:");
            }
        }
    }
}
