package com.example.lab4.controller;

import com.example.lab4.entity.Clinic;
import com.example.lab4.repository.ClinicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/clinic")
public class ClinicController {

    @Autowired
    private ClinicRepository clinicaRepository;

    @GetMapping
    public String listarClinicas(Model model) {
        model.addAttribute("listaClinicas", clinicaRepository.findAll());
        return "clinica/lista";
    }

    @GetMapping("/nuevo")
    public String nuevaClinica(Model model) {
        model.addAttribute("clinica", new Clinic());
        return "clinica/formulario";
    }

    @PostMapping("/guardar")
    public String guardarClinica(@ModelAttribute("clinica") @Valid Clinic clinic, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "clinica/formulario";
        }
        clinicaRepository.save(clinic);
        return "redirect:/clinicas";
    }

    @GetMapping("/editar/{id}")
    public String editarClinica(@PathVariable("id") Integer id, Model model) {
        Optional<Clinic> optional = clinicaRepository.findById(id);
        if (optional.isPresent()) {
            model.addAttribute("clinica", optional.get());
            return "clinica/formulario";
        } else {
            return "redirect:/clinicas";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarClinica(@PathVariable("id") Integer id) {
        clinicaRepository.deleteById(id);
        return "redirect:/clinicas";
    }


}