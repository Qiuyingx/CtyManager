package com.gen;

public class StartProject {

	public static void main(String[] args) throws Exception {
		GenProcesser processer = new GenProcesser();
		processer.init();
		processer.startGen();
	}
}
