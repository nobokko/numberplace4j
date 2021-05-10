package com.nobokko.numberplace4j.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.nobokko.numberplace4j.service.SudokuService;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("sudoku")
public class SudokuController {
    private SudokuService sudokuService;

    @Autowired
    public SudokuController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @RequestMapping("")
    public String get(@RequestParam(required = false, defaultValue = "new") String request, @RequestParam Map<String, String> params, Model model) {
        Pattern cellparam = Pattern.compile("cell\\[([0-9]+)\\]\\[([0-9]+)\\]");
        Integer[][] cells = new Integer[9][9];

        if ("new".equals(request)) {

        } else {
            for (String param_key : params.keySet()) {
                Matcher m = cellparam.matcher(param_key);
                if (m.matches()) {
                    String row = m.group(1);
                    String column = m.group(2);
                    try {
                        cells[Integer.parseInt(row)][Integer.parseInt(column)] = Integer.parseInt(params.get(param_key));
                    } catch (NumberFormatException nfe) {}
                }
            }
            if ("solve".equals(request)) {
                cells = this.sudokuService.solved(cells);
            } else {
                System.out.println(request);
            }
        }

        model.addAttribute("cells", cells);

        return "sudoku";
    }

}
