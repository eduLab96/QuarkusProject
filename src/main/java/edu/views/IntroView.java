package edu.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import jakarta.inject.Inject;

@PageTitle("Intro")
@Route(value = "", layout = MainLayout.class)
public class IntroView extends VerticalLayout {
	
	@Inject
    public IntroView() {
        setSpacing(false);

        H1 header = new H1("WELCOME TO MY QUARKUS PROJECT");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}

