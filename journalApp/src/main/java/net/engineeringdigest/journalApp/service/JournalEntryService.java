package net.engineeringdigest.journalApp.service;

import jakarta.transaction.Transactional;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class JournalEntryService {

    @Autowired
    public JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveNewEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userService.findByUsername(userName);
            journalEntry.setUser(user);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            if (user.getJournalEntries() == null) {
                user.setJournalEntries(new ArrayList<>()); // Initialize if null
            }
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error saving journal entry");
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(long id) {
        return journalEntryRepository.findById(id);
        // Optional data return value means that the data can be present in the data structure or not....
    }

    @Transactional
    public boolean deleteById(long id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUsername(userName);
            removed = user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId() == id);
            if (removed) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
            return removed;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting journal entry", e);
        }
    }
}

// controller -> service -> repository