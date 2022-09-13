package com.step.springmvcapp.service;

import com.itstep.myfirstspringapp.repository.CandidateDetailsRepository;
import com.itstep.myfirstspringapp.repository.CandidateRepository;
import com.itstep.myfirstspringapp.repository.ElectorRepository;
import com.step.springmvcapp.entity.Candidate;
import com.step.springmvcapp.entity.CandidateDetails;
import com.step.springmvcapp.entity.Elector;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Collection;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.NoResultException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class VoitingServiceImpl implements VoitingService { 
    
    @Autowired
    private CandidateRepository candidateDao;
    @Autowired
    private ElectorRepository electorDao;
    @Autowired
    private CandidateDetailsRepository detailsDao;

    public VoitingServiceImpl() {}     

    @Override
    @Transactional(readOnly = true) // в 10 раз быстрее будет работать, только для чтения
    public List<Candidate> findCandidates() {
        //List<Candidate> candidates = candidateDao.findAllCandidates();
        List<Candidate> candidates = candidateDao.findAll();
        return candidates;
    }
    

    @Override
    @Transactional(readOnly = true) // в 10 раз быстрее будет работать, только для чтения
    public Candidate findCandidateById(Long id) {
        Candidate candidate = candidateDao.findOne(id);
        return candidate;
    }

    @Override
    @Transactional // добавляет транзакцию!
    public void saveCandidate(Candidate candidate) {
        candidateDao.save(candidate);
    }

    @Override
    @Transactional(readOnly = true) // в 10 раз быстрее будет работать, только для чтения
    public List <Elector> findElectors() {
       return electorDao.findAll();
    }

    @Override
    @Transactional(readOnly = true) // в 10 раз быстрее будет работать, только для чтения
    public Elector findElectorById(Long id) {
        return electorDao.findOne(id);        
    }

    @Override
    @Transactional
    public void saveElector(Elector elector) {
        electorDao.save(elector);
       
    }
    
    @Override
    @Transactional
    public void vote (Long electorId, Long candidateId){
        Elector elector = findElectorById(electorId);
        Candidate candidate =  findCandidateById(candidateId);      
        Long voices = candidate.getVoices();
        if (voices==null)
           voices = 0L; 
        voices++;
        candidate.setVoices(voices);
        candidate.getElectors().add(elector);
        elector.setCandidate(candidate);
        elector.setVoted(true);
        saveCandidate(candidate);
        saveElector(elector);
    }
    
    @Transactional
    @Override
    public void saveCandidateDetails(CandidateDetails details) {
        detailsDao.save(details);
    }
    
    @Transactional (readOnly = true)
    @Override
    public CandidateDetails findCandidateDetailsById(Long id){ 
         return detailsDao.findOne(id);
    }
    
    @Override
    public String hashPassword (String password ){
/*Принцип значения соли очень прост, то есть сначала объедините пароль и содержимое, указанное значением соли.
  Таким образом, даже если пароль является очень распространенной строкой и добавляется имя пользователя, 
  окончательно рассчитанное значение  не так легко угадать.
  Поскольку злоумышленник не знает значение соли, трудно восстановить исходный пароль.
*/
        String salt = "1234"; // 
        char[] passwordChars = password.toCharArray(); 
        byte[] saltBytes = salt.getBytes();
        String hashedString="";
        //StringBuilder builder = new StringBuilder();
         try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            //Длина хэша sha512 составляет 512 бит = 64 байта/символа
            KeySpec ks = new PBEKeySpec(passwordChars,saltBytes,10000,32);
            SecretKey key = f.generateSecret(ks);
            byte[] res = key.getEncoded();
            hashedString = Hex.encodeHexString(res);  
         } catch (NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
         }
        return hashedString;
     }
 
}
