package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface JabatanService {
	void addJabatan(JabatanModel jabatan);
	void deleteJabatanById(long id);
	JabatanModel findJabatanByName(String name);
	JabatanModel findJabatanById(long id);
	List<JabatanModel> getAllJabatan();
}
