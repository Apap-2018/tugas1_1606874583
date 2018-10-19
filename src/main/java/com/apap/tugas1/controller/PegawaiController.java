package com.apap.tugas1.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;


@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> jabatanList = jabatanService.getAllJabatan();
		List<InstansiModel> instansiList = instansiService.getAllInstansi();
				model.addAttribute("jabatanList", jabatanList);
				model.addAttribute("instansiList", instansiList);
		return "home";
	}
	
	@RequestMapping("/response")
	private String response(Model model) {
		List<JabatanModel> jabatanList = jabatanService.getAllJabatan();
				model.addAttribute("jabatanList", jabatanList);
		return "response";
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
	
	@RequestMapping("/pegawai/termuda-tertua")
	private String viewTermudaTertua(@RequestParam("instansi") long id, Model model) {
		InstansiModel instansi = instansiService.findJabatanById(id);
		List<PegawaiModel> listPegawaiInstansi = instansi.getPegawaiInstansi();
		
		PegawaiModel pegawaiMuda;
		PegawaiModel pegawaiTua;
		
		if(listPegawaiInstansi.size()>0) {
			pegawaiMuda = listPegawaiInstansi.get(1);
			pegawaiTua = listPegawaiInstansi.get(1);
		
			for (PegawaiModel pegawaiTarget : listPegawaiInstansi) {
				Date ttlTarget = pegawaiTarget.getTanggalLahir();
				if(ttlTarget.before(pegawaiTua.getTanggalLahir())) {
					pegawaiTua = pegawaiTarget;
				}else if(ttlTarget.after(pegawaiMuda.getTanggalLahir())) {
					pegawaiMuda = pegawaiTarget;
				}
			}
			
			model.addAttribute("pegawaiMuda", pegawaiMuda);
			model.addAttribute("pegawaiTua", pegawaiTua);
			return "data-pegawai-tertua-termuda";
		}
				
		return "response";
	}
	
}
