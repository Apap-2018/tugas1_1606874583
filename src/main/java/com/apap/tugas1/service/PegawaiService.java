package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	void addPegawai(PegawaiModel pegawai);
	PegawaiModel findPegawaiByNip(String nip);
}
