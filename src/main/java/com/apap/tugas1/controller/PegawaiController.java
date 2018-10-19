package com.apap.tugas1.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	
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
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String tambahPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setInstansi(new InstansiModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getProvinsiList());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegwawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String nip = "";
		
		nip += pegawai.getInstansi().getId();
		
		String[] tglLahir = pegawai.getTanggalLahir().toString().split("-");
		String tglLahirString = tglLahir[2] + tglLahir[1] + tglLahir[0].substring(2, 4);
		nip += tglLahirString;

		nip += pegawai.getTahunMasuk();

		int counterSama = 1;
		for (PegawaiModel pegawaiInstansi:pegawai.getInstansi().getPegawaiInstansi()) {
			if (pegawaiInstansi.getTahunMasuk().equals(pegawai.getTahunMasuk()) && pegawaiInstansi.getTanggalLahir().equals(pegawai.getTanggalLahir())) {
				counterSama += 1;
			}	
		}
		nip += "0" + counterSama;

		for (JabatanModel jabatan:pegawai.getJabatanList()) {
			System.out.println(jabatan.getNama());
		}
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "tambah-pegawai-sukses";
	}
	
	
	@RequestMapping("/pegawai/termuda-tertua")
	private String viewTermudaTertua(@RequestParam("instansi") long id, Model model) {
		InstansiModel instansi = instansiService.findInstansiById(id);
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
			return "data-pegawai-termuda-tertua";
		}
				
		return "response";
	}
	
}
