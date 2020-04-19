package Game;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int COLUMN_NUMBER = 5;
    private static final int ROW_NUMBER = 5;
    private static final int SIZE_WIN =4;
    private static final char[][] GAME_FIELD = new char[ROW_NUMBER][COLUMN_NUMBER];
    private static final char PLAYER = 'X';
    private static final char PK = 'O';
    private static final char EMPTY = '*';

    private static Scanner sc = new Scanner(System.in);
    private static Random random = new Random();

    private static void initGameField() {
        for (int i = 0; i < ROW_NUMBER; i++) {
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                GAME_FIELD[i][j] = EMPTY;
            }
        }
    }

    private static void printGameField() {
        System.out.println("-------");
        for (int i = 0; i < ROW_NUMBER; i++) {
            System.out.print("|");
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                System.out.print(GAME_FIELD[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println("-------");
    }

    private static void playerStep() {
        int x,y;
        do {
            System.out.println("Введите кординаты:");
            x = checkLine(sc.nextLine()) - 1;
            y = checkLine(sc.nextLine()) - 1;
        } while ( x < 0 || y < 0 || x > ROW_NUMBER - 1 || y > COLUMN_NUMBER - 1 || GAME_FIELD[y][x] != EMPTY);
        GAME_FIELD[y][x] = PLAYER;
    }

    private static void pkStep() {
        int x,y;


        do {
            x = random.nextInt(ROW_NUMBER);
            y = random.nextInt(COLUMN_NUMBER);
        } while ( x < 0 || y < 0 || x > ROW_NUMBER - 1 || y > COLUMN_NUMBER - 1 || GAME_FIELD[y][x] != EMPTY);
        GAME_FIELD[y][x] = PK;
    }

    //проверка победы
    private static boolean checkWinnable(char dot) {
        for (int v = 0; v<COLUMN_NUMBER; v++){
            for (int h= 0; h<ROW_NUMBER; h++) {
                //анализ наличие поля для проверки
                if (h + SIZE_WIN <= COLUMN_NUMBER) {                           //по горизонтале
                    if (checkLineHorisont(v, h, dot) >= SIZE_WIN) return true;

                    if (v - SIZE_WIN > -2) {                            //вверх по диагонале
                        if (checkDiaUp(v, h, dot) >= SIZE_WIN) return true;
                    }
                    if (v + SIZE_WIN <= ROW_NUMBER) {                       //вниз по диагонале
                        if (checkDiaDown(v, h, dot) >= SIZE_WIN) return true;
                    }
                }
                if (v + SIZE_WIN <= ROW_NUMBER) {                       //по вертикале
                    if (checkLineVertical(v, h, dot) >= SIZE_WIN) return true;
                }
            }
        }
        return false;
    }


    //проверка заполнения всей линии по диагонале вверх

    private static int checkDiaUp(int v, int h, char dot) {
        int count=0;
        for (int i = 0, j = 0; j < SIZE_WIN; i--, j++) {
            if ((GAME_FIELD[v+i][h+j] == dot)) count++;
        }
        return count;
    }
    //проверка заполнения всей линии по диагонале вниз

    private static int checkDiaDown(int v, int h, char dot) {
        int count=0;
        for (int i = 0; i < SIZE_WIN; i++) {
            if ((GAME_FIELD[i+v][i+h] == dot)) count++;
        }
        return count;
    }

    private static int checkLineHorisont(int v, int h, char dot) {
        int count=0;
        for (int j = h; j < SIZE_WIN + h; j++) {
            if ((GAME_FIELD[v][j] == dot)) count++;
        }
        return count;
    }
    //проверка заполнения всей линии по вертикале
    private static int checkLineVertical(int v, int h, char dot) {
        int count=0;
        for (int i = v; i< SIZE_WIN + v; i++) {
            if ((GAME_FIELD[i][h] == dot)) count++;
        }
        return count;
    }

    private static boolean checkGameFieldFull() {
        for (int i = 0; i < ROW_NUMBER; i++) {
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                if (GAME_FIELD[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int checkLine(String line) {
        try {
            return Integer.parseInt(line);
        } catch (Exception e) {
            throw new RuntimeException("нужно вводить число!");
        }
    }

    public static void main(String[] args) {
        initGameField();
        printGameField();

        while (!checkGameFieldFull()) {
            playerStep();
            printGameField();

            if (checkWinnable(PLAYER)) {
                System.out.println("Победил игрок!");
                break;
            }

            if (checkGameFieldFull()) {
                System.out.println("Ничья!");
                break;
            }

            pkStep();
            printGameField();

            if (checkWinnable(PK)) {
                System.out.println("Победил pk");
                break;
            }

            if (checkGameFieldFull()) {
                System.out.println("Ничья!");
                break;
            }
        }
    }
}