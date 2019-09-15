package com.stefanini.application.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stefanini.application.service.AvaliacaoProfissionalService;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

	@Autowired
	AvaliacaoProfissionalService avaliacaoProfissionalService;

	// @Scheduled(cron = "0 0 0 1 * ?") // every month
	// @Scheduled(cron = "0 0 0 * * ?") // every day
	@Scheduled(cron = "0 * * * * ?") // every minute (test)
	public void processarNovasAvaliacoes() {
		Integer count = 0;
		try {
			log.info("Inicializando o processamento das novas avaliações :: {}", dateFormat.format(new Date()));
			count = avaliacaoProfissionalService.processarNovasAvaliacoes();
		} catch (Exception e) {
			log.error("Ocorreu um erro no processamento das novas avaliações!", e);
		} finally {
			log.info("Finalizando o processamento das [{}] novas avaliações :: {}", count, dateFormat.format(new Date()));
		}
	}

}
