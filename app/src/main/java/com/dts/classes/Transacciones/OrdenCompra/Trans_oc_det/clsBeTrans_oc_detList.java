package com.dts.classes.Transacciones.OrdenCompra.Trans_oc_det;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeTrans_oc_detList {
    @ElementList(inline=true,required = false)
    public List<clsBeTrans_oc_det> items = new List<clsBeTrans_oc_det>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeTrans_oc_det> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeTrans_oc_det clsBeTrans_oc_det) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeTrans_oc_det> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeTrans_oc_det> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeTrans_oc_det get(int index) {
            return null;
        }

        @Override
        public clsBeTrans_oc_det set(int index, clsBeTrans_oc_det element) {
            return null;
        }

        @Override
        public void add(int index, clsBeTrans_oc_det element) {

        }

        @Override
        public clsBeTrans_oc_det remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeTrans_oc_det> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeTrans_oc_det> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeTrans_oc_det> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}