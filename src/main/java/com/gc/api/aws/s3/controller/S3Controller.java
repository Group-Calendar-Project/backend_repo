package com.gc.api.aws.s3.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gc.api.aws.s3.docs.S3ControllerDocs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller implements S3ControllerDocs {
}
