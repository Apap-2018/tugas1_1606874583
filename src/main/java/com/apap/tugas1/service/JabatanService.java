package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface JabatanService {
	void addJabatan(JabatanModel jabatan);
	JabatanModel findJabatanByName(String name);
	List<JabatanModel> getAllJabatan();
}
