package com.nobokko.numberplace4j.service;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.springframework.stereotype.Service;

@Service
public class SudokuService {
    private VecInt vi = new VecInt();

    public SudokuService() {
        vi.clear();
    }

    private int valiable(int row, int column, int num) {
        return row * 9 * 9 + column * 9 + num + 1;
    }

    public Integer[][] solved(Integer[][] cells) {
        ISolver solver;
        solver = SolverFactory.newDefault();
        solver.newVar(1000); // > 9(縦)*9(横)*9(1-9)+1(0は使えない)
        int row = 0;
        int column = 0;
        int num = 0;
        try {
            // 一行目のいずれかの列のマスは1である
            vi.clear();
            vi.push(valiable(row, 0, num));
            vi.push(valiable(row, 1, num));
            vi.push(valiable(row, 2, num));
            vi.push(valiable(row, 3, num));
            vi.push(valiable(row, 4, num));
            vi.push(valiable(row, 5, num));
            vi.push(valiable(row, 6, num));
            vi.push(valiable(row, 7, num));
            vi.push(valiable(row, 8, num));
            solver.addClause(vi);
            // 一行目の一列目と二列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 0, num));
            vi.push(-valiable(row, 1, num));
            solver.addClause(vi);
            // 一行目の一列目と三列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 0, num));
            vi.push(-valiable(row, 2, num));
            solver.addClause(vi);
            // 一行目の一列目と四列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 0, num));
            vi.push(-valiable(row, 3, num));
            solver.addClause(vi);
            // 一行目の一列目と五列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 0, num));
            vi.push(-valiable(row, 4, num));
            solver.addClause(vi);
            // 一行目の一列目と六列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 0, num));
            vi.push(-valiable(row, 5, num));
            solver.addClause(vi);
            // 一行目の一列目と七列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 0, num));
            vi.push(-valiable(row, 6, num));
            solver.addClause(vi);
            // 一行目の一列目と八列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 0, num));
            vi.push(-valiable(row, 7, num));
            solver.addClause(vi);
            // 一行目の一列目と九列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 0, num));
            vi.push(-valiable(row, 8, num));
            solver.addClause(vi);
            // 一行目の二列目と三列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 1, num));
            vi.push(-valiable(row, 2, num));
            solver.addClause(vi);
            // 一行目の二列目と四列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 1, num));
            vi.push(-valiable(row, 3, num));
            solver.addClause(vi);
            // 一行目の二列目と五列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 1, num));
            vi.push(-valiable(row, 4, num));
            solver.addClause(vi);
            // 一行目の二列目と六列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 1, num));
            vi.push(-valiable(row, 5, num));
            solver.addClause(vi);
            // 一行目の二列目と七列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 1, num));
            vi.push(-valiable(row, 6, num));
            solver.addClause(vi);
            // 一行目の二列目と八列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 1, num));
            vi.push(-valiable(row, 7, num));
            solver.addClause(vi);
            // 一行目の二列目と九列目のマスは同時に1にならない
            vi.clear();
            vi.push(-valiable(row, 1, num));
            vi.push(-valiable(row, 8, num));
            solver.addClause(vi);
            // 以下同様
            for (int i = 2; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    vi.clear();
                    vi.push(-valiable(row, i, num));
                    vi.push(-valiable(row, j, num));
                    solver.addClause(vi);
                }
            }

            // 一行目のいずれかの列のマスは2である
            num = 1;
            vi.clear();
            for (int i = 0; i < 9; i++) {
                vi.push(valiable(row, i, num));
            }
            solver.addClause(vi);
            // 一行目のマスは同時に2にならない
            for (int i = 0; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    vi.clear();
                    vi.push(-valiable(row, i, num));
                    vi.push(-valiable(row, j, num));
                    solver.addClause(vi);
                }
            }

            // 一行目のいずれかの列のマスは3～9である
            // また一行目のマスは同時に3～9にならない
            for (num = 2; num < 9; num++) {
                vi.clear();
                for (int i = 0; i < 9; i++) {
                    vi.push(valiable(row, i, num));
                }
                solver.addClause(vi);
                // 一行目のマスは同時に2にならない
                for (int i = 0; i < 9; i++) {
                    for (int j = i + 1; j < 9; j++) {
                        vi.clear();
                        vi.push(-valiable(row, i, num));
                        vi.push(-valiable(row, j, num));
                        solver.addClause(vi);
                    }
                }
            }

            // 二～九行目のいずれかの列のマスは1～9である
            // また二～九行目のマスは同時に1～9にならない
            for (row = 1; row < 9; row++) {
                for (num = 0; num < 9; num++) {
                    vi.clear();
                    for (int i = 0; i < 9; i++) {
                        vi.push(valiable(row, i, num));
                    }
                    solver.addClause(vi);
                    // 一行目のマスは同時に1～9にならない
                    for (int i = 0; i < 9; i++) {
                        for (int j = i + 1; j < 9; j++) {
                            vi.clear();
                            vi.push(-valiable(row, i, num));
                            vi.push(-valiable(row, j, num));
                            solver.addClause(vi);
                        }
                    }
                }
            }

            // 同様に一～九列目のいずれかの行のマスは1[～9]である
            // また一～九列目のマスは同時に1[～9]にならない
            for (column = 0; column < 9; column++) {
                for (num = 0; num < 9; num++) {
                    vi.clear();
                    for (int i = 0; i < 9; i++) {
                        vi.push(valiable(i, column, num));
                    }
                    solver.addClause(vi);
                    // 一～九行目のマスは同時に1[～9]にならない
                    for (int i = 0; i < 9; i++) {
                        for (int j = i + 1; j < 9; j++) {
                            vi.clear();
                            vi.push(-valiable(i, column, num));
                            vi.push(-valiable(j, column, num));
                            solver.addClause(vi);
                        }
                    }
                }
            }
            // 同様に3x3の範囲でマスは同時に1[～9]にならない
            for (num = 0; num < 9; num++) {
                for (row = 0; row < 9; row += 3) {
                    for (column = 0; column < 9; column += 3) {
                        for (int row_i = row; row_i < row + 3; row_i++) {
                            for (int column_i = column; column_i < column + 3; column_i++) {
                                int v1 = -valiable(row_i, column_i, num);
                                for (int column_j = column_i + 1; column_j < column + 3; column_j++) {
                                    vi.clear();
                                    vi.push(v1);
                                    vi.push(-valiable(row_i, column_j, num));
                                    solver.addClause(vi);
                                }
                                for (int row_j = row_i + 1; row_j < row + 3; row_j++) {
                                    for (int column_j = column; column_j < column + 3; column_j++) {
                                        vi.clear();
                                        vi.push(v1);
                                        vi.push(-valiable(row_j, column_j, num));
                                        solver.addClause(vi);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // 同じマスに複数の数字は入らない
            for (row = 0; row < 9; row++) {
                for (column = 0; column < 9; column++) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = i + 1; j < 9; j++) {
                            vi.clear();
                            vi.push(-valiable(row, column, i));
                            vi.push(-valiable(row, column, j));
                            solver.addClause(vi);
                        }
                    }
                }
            }

            // 指定の場所はその数字である
            for (row = 0; row < 9; row++) {
                for (column = 0; column < 9; column++) {
                    if (cells[row][column] != null && cells[row][column] != 0) {
                        vi.clear();
                        vi.push(valiable(row, column, cells[row][column] - 1));
                        solver.addClause(vi);
                    }
                }
            }

            try {
                if (solver.isSatisfiable()) {
                    int[] m = solver.model();
                    for (int i = 0; i < m.length; i++) {
                        if (m[i] > 0) {
                            row = (m[i]-1) / (9 * 9);
                            column = ((m[i]-1) - row * 9 * 9) / 9;
                            num = ((m[i]-1) - row * 9 * 9 - column * 9);

                            System.out.printf("%d@%d,%d,%d\n", m[i], row + 1, column + 1, num + 1);

                            cells[row][column] = num + 1;
                        }
                    }
                } else {
                    System.out.println("not Satisfiable.");
                }
            } catch (TimeoutException e) {
                // Auto-generated catch block
                e.printStackTrace();
            }
        } catch (ContradictionException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }

        return cells;
    }
}
