package com.apap.tugas1.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;


@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	
	@Autowired
	private JabatanDb jabatanDb;

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
		
	}

	@Override
	public List<JabatanModel> getAllJabatan() {
		List<JabatanModel> listnya = jabatanDb.findAll();
		return listnya;
	}

	@Override
	public JabatanModel findJabatanByName(String name) {
		List<JabatanModel> listnya = jabatanDb.findAll();
		for(JabatanModel jabatan:listnya) {
			if (jabatan.getNama().equals(name)) {
				return jabatan;
			}
		}
		
		return null;
	}

	@Override
	public JabatanModel findJabatanById(long id) {
		// TODO Auto-generated method stub
		return jabatanDb.getOne(id);
	}

	@Override
	public void deleteJabatanById(long id) {
		// TODO Auto-generated method stub
		jabatanDb.deleteById(id);
	}


	
}
