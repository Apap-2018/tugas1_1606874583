package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;


@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> jabatanList = jabatanService.getAllJabatan();
				model.addAttribute("jabatanList", jabatanList);
		return "home";
	}
	
	
	@RequestMapping("/jabatan")
	private String viewDeskripsiJabatan(@RequestParam("jabatan") String jabatan, Model model) {
		JabatanModel jabatanTarget = jabatanService.findJabatanByName(jabatan);
		model.addAttribute("jabatanTarget", jabatanTarget);
		return "deskripsi-jabatan";
	}
	
	@RequestMapping("/pegawai")
	private String viewDetailPegawai(@RequestParam("nip") String nip, Model model) {
		double gaji = 0;
		PegawaiModel pegawai = pegawaiService.findPegawaiByNip(nip);
		
		
		for(JabatanModel jabatan : pegawai.getJabatanList()) {
			double gajiDummy = jabatan.getGajiPokok();
			if(gaji < gajiDummy) {
				gaji = gajiDummy;
			}
		}
		
		double tunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
		double gajiTotal = gaji + ((tunjangan/100) * gaji);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gajiPegawai", gajiTotal);
		return "data-pegawai";
		
	}
	
}