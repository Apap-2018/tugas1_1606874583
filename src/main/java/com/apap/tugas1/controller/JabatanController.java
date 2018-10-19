package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "tambah-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		return "tambah-jabatan-sukses";
	}
	
	@RequestMapping("/jabatan/view")
	private String viewDeskripsiJabatan(@RequestParam("jabatan") long id, Model model) {
		JabatanModel jabatan = jabatanService.findJabatanById(id);
		model.addAttribute("jabatan", jabatan);
		return "data-jabatan";
	}
	
	@RequestMapping("/jabatan/viewall")
	private String home(Model model) {
		List<JabatanModel> jabatanList = jabatanService.getAllJabatan();
				model.addAttribute("jabatanList", jabatanList);
		return "data-semua-jabatan";
	}
	
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String ubahJabatan(@RequestParam("idJabatan") long id, Model model) {
		JabatanModel jabatan = jabatanService.findJabatanById(id);
		model.addAttribute("jabatan", jabatan);
		System.out.println(id);
		return "ubah-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		return "ubah-jabatan-sukses";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.GET)
	private String hapusJabatan(@RequestParam("idJabatan") long id, Model model) {

		if(jabatanService.findJabatanById(id).getPegawaiList().size() > 0) {
			return "hapus-jabatan-gagal";
		}
		
		jabatanService.deleteJabatanById(id);
		return "hapus-jabatan-sukses";
		
	}
	
	
}
