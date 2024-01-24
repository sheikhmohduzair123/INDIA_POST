package com.controllers;

import com.constants.Status;
import com.domain.Client;
import com.domain.Parcel;
import com.domain.Services;
import com.domain.User;
import com.repositories.ClientRepository;
import com.repositories.SUserRepository;
import com.services.ClientService;
import com.services.DataSyncService;
import com.services.ParcelService;
import com.services.ParcelSyncService;
import com.services.impl.UserDetailsServiceImpl;
import com.util.ServerTokenUtils;
import com.vo.RefillAmountRequestVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.print.PrintServiceLookup;
import java.security.Principal;
import java.util.List;

@Controller
@Profile({ "client", "clientonly" })
@RequestMapping("/client")
public class ClientPageControlers {

    protected Logger log = LoggerFactory.getLogger(ClientPageControlers.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    DataSyncService dataSyncService;

    @Autowired
    ParcelService parcelService;

    @Autowired
    private UserDetailsServiceImpl userUpdateService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    ParcelSyncService parcelSyncService;
    /*
     * @Autowired private SyncTableRepository syncTableRepository;
     */
    @Autowired
    private SUserRepository sUserRepository;

    @Value("${server.url}")
    private String serverURL;

    @Value("${postalCode}")
    private String postalCode;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${enable.pdf.download}")
    private String enablePdfDownload;

    @Value("${volumetric.factor}")
    private float volumetricFactor;

    @Autowired
    ServerTokenUtils tokenUtils;

    // public static String globalServerToken;

    /*
     * @Value("${trust.store}") private Resource trustStore;
     *
     *
     * @Value("${trust.store.password}") private String trustStorePassword;
     */

    @Value("${app.baseurl}")
    private String baseurl;

    @Value("${cookie.maxage}")
    private String cookieMaxAge;

    /*
     * This will be default landing page for now where user/ will land post
     * successful login Here user will fill up sender details, then successive steps
     * will be followed. We will save the sender details in DB then move on to
     * receiver details page.
     */
    @RequestMapping(value = "/senderHome", method = RequestMethod.GET)
    public String senderHome(Model model, Principal principal) {
        log.info("sender detials retrieved, moving on to receiver details");
        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        // if user has logined first time & passowrd has not been changed, forcefully
        // movw to change password
        if (loginedUser.isFirstLogin()) {
            return "redirect:/changePasswordHome";
        }

        List<Services> postalServiceList = parcelService.fetchPostalSerives();
        List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved",
                Enum.valueOf(Status.class, "ACTIVE"));
        model.addAttribute("postalServiceList", postalServiceList);
        List<String> dailyData = parcelService.getParcelCountCollection(clientList.get(0).getPostalCode());
        model.addAttribute("dailyData", dailyData);

        String name = userUpdateService.getUserByUsername(loginedUser.getUsername());
        model.addAttribute("user", name);
        model.addAttribute("postalCode", clientList.get(0).getPostalCode());
        model.addAttribute("role", loginedUser.getRole().getName());
        model.addAttribute("enablePdfDownload", enablePdfDownload);
        model.addAttribute("volumetricFactor", volumetricFactor);
        if (PrintServiceLookup.lookupDefaultPrintService() != null) {
            String printer = PrintServiceLookup.lookupDefaultPrintService().getName();
            model.addAttribute("printer", printer);
        }
        return "default";
    }

    /** For calculating the net price of parcel for Information purpose **/
    @RequestMapping(value = "/netPrice", method = RequestMethod.GET)
    public String calculatePrice(Model model, Principal principal) {
        log.info("Check net payable amount");
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        // if user has logined first time & passowrd has not been changed, forcefully
        // movw to change password
        if (loginedUser.isFirstLogin()) {
            return "redirect:/changePasswordHome";
        }
        List<Services> postalServiceList = parcelService.fetchPostalSerives();
        model.addAttribute("postalServiceList", postalServiceList);
        // List<String> dailyData =
        // parcelService.getParcelCountCollection(Integer.parseInt(postalCode));
        // model.addAttribute("dailyData", dailyData);
        String name = userUpdateService.getUserByUsername(loginedUser.getUsername());
        model.addAttribute("user", name);
        model.addAttribute("volumetricFactor", volumetricFactor);
        return "calculatePrice";
    }

    /**
     * It will redirect to the page where user can Cancel and Reprint the Parcel
     *
     * @param model
     * @param principal
     * @return Cancel and Reprint Page
     */
    @RequestMapping(path = "/searchParcel", method = RequestMethod.GET)
    public ModelAndView searchParcel(Model model, Principal principal) {
        log.info("inside fetch top 5 recent transactions in search parcel");
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        ModelAndView modelAndView = new ModelAndView();

        // if user has logined first time & passowrd has not been changed, forcefully
        // movw to change password
        if (loginedUser.isFirstLogin()) {
            modelAndView.setViewName("redirect:/changePasswordHome");
            return modelAndView;
        }

        model.addAttribute("user", loginedUser.getName());
        List<Parcel> parcels = parcelService.getRecentParcels(5);
        model.addAttribute("parcels", parcels);
        model.addAttribute("enablePdfDownload", enablePdfDownload);
        if (PrintServiceLookup.lookupDefaultPrintService() != null) {
            String printer = PrintServiceLookup.lookupDefaultPrintService().getName();
            model.addAttribute("printer", printer);
        }

        modelAndView.setViewName("searchParcel");
        log.info("Returning Search Parcel Page");

        return modelAndView;
    }

    @RequestMapping("/getReportPage")
    public String getReport(Model model, Principal principal) {
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        // if user has logined first time & passowrd has not been changed, forcefully
        // movw to change password
        if (loginedUser.isFirstLogin()) {
            return "redirect:/changePasswordHome";
        }

        model.addAttribute("user", loginedUser.getName());
        log.info("Returning Page report");

        return "reportPage";
    }

    @RequestMapping(path = "/addFunds", method = RequestMethod.GET)
    public ModelAndView addFunds(Model model, Principal principal) {

        log.info("getting logined user");
        com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
        ModelAndView modelAndView = new ModelAndView();

        // if user has logined first time & passowrd has not been changed, forcefully
        // movw to change password
        if (loginedUser.isFirstLogin()) {
            modelAndView.setViewName("redirect:/changePasswordHome");
            return modelAndView;
        }

        model.addAttribute("user", loginedUser.getName());

        log.debug("returning add funds page");
        modelAndView.setViewName("addFunds");
        
        return modelAndView;
    }

    @RequestMapping(value = "/commissionClient", method = RequestMethod.GET)
    public String commissionClient(Model model) {
        log.debug("opened commission client page");

        if (clientService.getClientDetailsLocal() && userUpdateService.getUserDetailsLocal()) {
            return "redirect:/login";
        }
        return "commissionClient";
    }

    @RequestMapping(value = "/commissionClientOnly", method = RequestMethod.GET)
    public String commissionClientOnly(Model model) {
        log.debug("opened commission client page");
        return "commissionClientOnly";
    }
}
