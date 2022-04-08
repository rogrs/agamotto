package br.com.rogrs.agamotto.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;


@Service
public class LoadFromFile {

	private ResourceLoader resourceLoader;

	@Autowired
	public LoadFromFile(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public LoadFromFile() {
	}

	public File getFile(String filename) throws IOException {
		Resource resource = resourceLoader.getResource(filename);

		Path path = Paths.get(resource.getURI());
		
		return path.toFile();

	}

}
