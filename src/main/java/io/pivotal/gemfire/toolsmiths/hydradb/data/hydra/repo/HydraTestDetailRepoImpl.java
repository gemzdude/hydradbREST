package io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.repo;

import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetail;
import io.pivotal.gemfire.toolsmiths.hydradb.data.hydra.HydraTestDetailExt;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class HydraTestDetailRepoImpl implements HydraTestDetailRepoCustom {

  private static Logger log = Logger.getLogger(HydraTestDetailRepoImpl.class);

  @Autowired
  HydraTestDetailRepo hydraTestDetailRepo;

  @Autowired
  HydraTestDetailExtRepo hydraTestDetailExtRepo;

  @Override
  public HydraTestDetail getOrCreateTestDetail(int runId,
                                               long testSuiteDetailId,
                                               long testId,
                                               String elapsedTime,
                                               String diskStr,
                                               String status,
                                               String error,
                                               String bugNumber,
                                               String comment,
                                               String tags,
                                               String logLocation)
  {
    HydraTestDetail htd = updateHydraTestDetailIfRecordExist(
        runId,
        testSuiteDetailId,
        testId,
        elapsedTime,
        diskStr,
        status,
        error,
        bugNumber,
        comment,
        tags,
        logLocation);

    if(htd != null) {
      return htd;
    }
//    log.info("Did not find any existing hydra test details for hydraTestId = " + testId
//        + ", hydraRunId : " + runId + " and hydraTestSuiteDetailId : " + testSuiteDetailId);

    hydraTestDetailRepo.createHydraTestDetail(elapsedTime, diskStr, status, error, bugNumber,
                                              testId, testSuiteDetailId, runId, comment, tags);

    htd = hydraTestDetailRepo.getHydraTestDetail(testId, testSuiteDetailId, runId);

    if(!"P".equalsIgnoreCase(htd.getStatus())) {
      HydraTestDetailExt htdx = new HydraTestDetailExt();
      htdx.setId(Integer.toUnsignedLong(htd.getId()));
      htdx.setLogLocation(logLocation);
      hydraTestDetailExtRepo.save(htdx);
    }

    return htd;

  }

  private HydraTestDetail updateHydraTestDetailIfRecordExist(int runId,
                                                        long testSuiteDetailId,
                                                        long testId,
                                                        String elapsedTime,
                                                        String diskStr,
                                                        String status,
                                                        String error,
                                                        String bugNumber,
                                                        String comment,
                                                        String tags,
                                                        String logLocation) {

    HydraTestDetail htd = hydraTestDetailRepo.getHydraTestDetail(testId, testSuiteDetailId, runId);

    if (htd != null) {
      String hdbBugNumber = htd.getBugNumber();
      String hdbTags = htd.getTags();
      String hdbComment = htd.getComment();
      String hdbStatus = htd.getStatus();

      if ((bugNumber != null) && (!bugNumber.equals(hdbBugNumber))
          || (tags != null && !tags.equals(hdbTags))
          || (comment != null && !comment.equals(hdbComment))
          || (status != null && !status.equals(hdbStatus))) {
        htd.setBugNumber(bugNumber);
        htd.setComment(comment);
        htd.setStatus(status);
        htd.setTags(tags);
        hydraTestDetailRepo.save(htd);
      }
    }
    return htd;
  }
}
